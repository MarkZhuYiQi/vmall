package com.mark.manager.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.mark.common.exception.CategoryException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.dao.CategoryDao;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.VproNavbarMapper;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.pojo.VproNavbarExample;
import com.mark.manager.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mark.common.constant.CategoryConstant.NAVBAR_HAS_NO_SUBSETS;
import static com.mark.common.constant.CategoryConstant.NAVBAR_NULL_BY_ID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    VproNavbarMapper vproNavbarMapper;
    @Autowired
    @Qualifier("categoryDao")
    CategoryDao categoryDao;

    @Autowired
    JedisClient jedisClient;

    @Value("${navbarTreePrefix}")
    String navbarTreePrefix;

    private List<VproNavbar> list = null;

    public List<VproNavbar> getList() {
        return list;
    }

    public void setList(List<VproNavbar> list) {
        this.list = list;
    }
    @Override
    public VproNavbar getCategoryById(Integer navId) throws CategoryException {
        // 这里的错误需要更好的处理
        return categoryDao.getCategoryById(navId);
    }

    /**
     * 获得导航列表
     * @return [VproNavbar, VproNavbar, VproNavbar, VproNavbar...]
     */
    @Override
    public List<VproNavbar> getCategories() {
        if (getList() == null || getList().size() == 0) {
            try {
                setList(categoryDao.getCategories());
            } catch (CategoryException e) {
                e.printStackTrace();
            }
        }
        return getList();
    }
    @Override
    public Map<Integer, VproNavbar> getCategoriesAsHashMap() {
        List<VproNavbar> list = getCategories();
        return list.stream().collect(Collectors.toMap(VproNavbar::getNavId, vproNavbar -> vproNavbar));
    }

    /**
     * 获得目录层级结构
     * [{navId: xx, subNav: [{...}, {...}, ...]}, {...}, ...]
     * @return List<CategoryNode>
     */
    @Override
    public List<CategoryNode>  getCategoriesTree() {
        List<VproNavbar> list = getCategories();
        try {
            return categoryDao.getCategoriesTree(list);
        } catch (CategoryException e) {
            Long time = System.currentTimeMillis();
            List<CategoryNode> navs = new ArrayList<CategoryNode>();
            for(int i = 0; i < list.size(); i++)
            {
                // 顶层目录
                if (list.get(i).getNavPid() == 0)
                {
                    // 转换成专属格式
                    CategoryNode categoryNode = DtoUtil.vproNavbar2CategoryNode(list.get(i));
                    // 收集该顶层目录下的子目录
                    CategoryNode nav = getSubCategory(categoryNode);
                    navs.add(nav);
//                    list.remove(i);
                }
            }
            /*Iterator<VproNavbar> vproNavbarIterator = list.iterator();
            while (vproNavbarIterator.hasNext()) {
                VproNavbar vproNavbar = vproNavbarIterator.next();
                if (vproNavbar.getNavPid() == 0) {
                    CategoryNode categoryNode = DtoUtil.vproNavbar2CategoryNode(vproNavbar);
                    vproNavbarIterator.remove();
                    CategoryNode nav = getSubCategory(categoryNode);
                    navs.add(nav);
                }
            }*/
            System.out.println("导航树消耗时间： " + String.valueOf(System.currentTimeMillis() - time) + "ms");
            jedisClient.set(navbarTreePrefix, JSON.toJSONString(navs));
            return navs;
        }
    }
    @Override
    // 给他一个默认参数，如果为空就放一个空List对象。
    public CategoryNode getSubCategory(CategoryNode categoryNode) {
        List<CategoryNode> subs = new ArrayList<CategoryNode>();
        return getSubCategory(categoryNode, subs);
    }
    @Override
    public CategoryNode getSubCategory(VproNavbar vproNavbar) {
        CategoryNode categoryNode = DtoUtil.vproNavbar2CategoryNode(vproNavbar);
        List<CategoryNode> subs = new ArrayList<CategoryNode>();
        return getSubCategory(categoryNode, subs);
    }

    /**
     * 迭代整个目录列表，判断传过来的目录是否-*docker
     * @param mainNav 主目录
     * @param subNavs 传入的ID所指目录的子目录list()
     * @return 子目录CategoryNode对象，subNav包含CategoryNode
     */
    public CategoryNode getSubCategory(CategoryNode mainNav, List<CategoryNode> subNavs) {
        List<VproNavbar> list = getCategories();
        for(int i = 0; i < list.size(); i++)
        {
            // 判断次级目录的父目录是否属于传来的目录
            if (list.get(i).getNavPid().equals(mainNav.getNavId()))
            {
                // 次级目录转换格式
                CategoryNode sub = DtoUtil.vproNavbar2CategoryNode(list.get(i));
                // 子目录的迭代结果，寻找该目录下是否还有子目录, 迭代寻找
                sub = getSubCategory(sub);
                subNavs.add(sub);
//                list.remove(i);
            }
        }
        /*Iterator<VproNavbar> vproNavbarIterator = list.iterator();
        while (vproNavbarIterator.hasNext()) {
            VproNavbar vproNavbar = vproNavbarIterator.next();
            if (vproNavbar.getNavPid().equals(mainNav.getNavId())) {
                CategoryNode sub = DtoUtil.vproNavbar2CategoryNode(vproNavbar);
                vproNavbarIterator.remove();
                sub = getSubCategory(sub);
                subNavs.add(sub);
            }
        }*/
        if (subNavs != null)
        {
            mainNav.setSubNav(subNavs);
        }
        return mainNav;
    }

    /**
     * 根据提供的id查找其子目录ids
     * @param navId 导航id
     * @param list 菜单目录集合
     * @param idList 最终菜单id集合
     * @return
     * 格式：List<Integer> [xxx,xx,xxx,xx,x,x,xxx]
     */
    @Override
    public List<Integer> getSubIdFromCategory(Integer navId, List<VproNavbar> list, List<Integer> idList) throws CategoryException {
        VproNavbar vproNavbar = getCategoryById(navId);
//        logger.info("CategoryServiceImpl： getSubIdFromCategory, navId: " + navId + ", navbarObj: " + vproNavbar.toString());
        if (!vproNavbar.getNavIsParent())
        {
            idList.add(vproNavbar.getNavId());
            return idList;
        }
        for(int i = list.size() - 1; i >= 0; i--)
        {
            if (list.get(i).getNavPid().equals(navId))
            {
                if (list.get(i).getNavIsParent()) {
                    getSubIdFromCategory(list.get(i).getNavId(), list, idList);
                } else {
                    idList.add(list.get(i).getNavId());
                }
            }
        }
        return idList;
    }
    @Override
    public List<Integer> getSubIdFromCategory(Integer navId) throws CategoryException {
        List<VproNavbar> list = getCategories();
        return getSubIdFromCategory(navId, list, new ArrayList<Integer>());
    }

    /**
     * 通过导航id获得该导航下的子导航id集合
     * [xx=[xxx], xxx=[xxx], ...]
     * 如果是顶级目录则获得每个顶级导航的子导航id集合
     * [xx=[xxx], xx=[xxx], xx=[xxx]]
     * @param navId
     * @return Map<Integer, List<Integer>> [xx=[xxx], xx=[xxx], xx=[xxx]]
     */
    @Override
    public Map<Integer, List<Integer>> getSubIds(Integer navId) throws CategoryException{
        Map<Integer, List<Integer>> navIds = new HashMap<Integer, List<Integer>>();
        List<VproNavbar> navbars = getCategories();
        List<CategoryNode> categoryNodes;
        if (navId == 0) {
//            logger.info("start navIds collect, navId: " + navId);
            // 如果传入的导航id为0，说明是顶级导航，直接拿到层级导航对象CategoryNode即可
            categoryNodes = getCategoriesTree();
        } else {
            // 传入了id，说明是在一个子导航中，生成子导航迭代对象CategoryNode
            VproNavbar navbar = getCategoryById(navId);
            if (!navbar.getNavId().equals(navId) || navbar.getNavId() == null) throw new CategoryException("navbar id error, no info detected.", NAVBAR_NULL_BY_ID);
            

            CategoryNode categoryNode = DtoUtil.vproNavbar2CategoryNode(navbar);
            categoryNode = getSubCategory(categoryNode);
            if (categoryNode.getSubNav().size() > 0) {
                categoryNodes = categoryNode.getSubNav();
            } else {
                throw new CategoryException("navbar has no subsets.", NAVBAR_HAS_NO_SUBSETS);
            }
        }
        for(CategoryNode c : categoryNodes) {
            navIds.put(c.getNavId(), getSubIdFromCategory(c.getNavId(), navbars, new ArrayList<Integer>()));
        }
        return navIds;
    }

    /**
     * 重载方法
     * @return
     */
    @Override
    public Map<Integer, List<Integer>> getSubIds() throws CategoryException {
        return getSubIds(0);
    }
    @Override
    public int addCategory(VproNavbar vproNavbar)
    {
        return  vproNavbarMapper.insert(vproNavbar);
    }
    @Override
    public int removeCategory(Integer id)
    {
        return vproNavbarMapper.deleteByPrimaryKey(id);
    }
    @Override
    public int modifyCategory(VproNavbar vproNavbar)
    {
        VproNavbarExample vproNavbarExample = new VproNavbarExample();
        vproNavbarExample.createCriteria().andNavIdEqualTo((int)vproNavbar.getNavId());
        return vproNavbarMapper.updateByExampleSelective(vproNavbar, vproNavbarExample);
    }

/*    public void categoryModify(Integer navId)
    {
        VproNavbarExample vproNavbarExample = new VproNavbarExample();
        vproNavbarExample.createCriteria().andNavIdEqualTo((int)navId);
        VproNavbar vproNavbar = new VproNavbar();
        vproNavbar.setNavIsParent(true);
        vproNavbarMapper.updateByExampleSelective(vproNavbar, vproNavbarExample);
    }*/
}
