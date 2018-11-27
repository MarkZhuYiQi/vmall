package com.mark.manager.serviceImpl;

import com.mark.common.pojo.CategoryNode;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.VproNavbarMapper;
import com.mark.manager.pojo.VproNavbar;
import com.mark.manager.pojo.VproNavbarExample;
import com.mark.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    VproNavbarMapper vproNavbarMapper;
    private List<VproNavbar> list;

    public VproNavbar getCategory(Integer navId)
    {
        return vproNavbarMapper.selectByPrimaryKey(navId);
    }

    public List<VproNavbar> getCategories() {
        VproNavbarExample vproNavbarExample = new VproNavbarExample();
        vproNavbarExample.createCriteria();
        list = vproNavbarMapper.selectByExample(vproNavbarExample);
        return list;
    }

    public Result getCategoriesAsHashMap() {
        List<VproNavbar> list = getCategories();
        Map<Integer, VproNavbar> map = list.stream().collect(Collectors.toMap(VproNavbar::getNavId, vproNavbar -> vproNavbar));
        return new Result(map);
    }

    public Result getCategoriesTree() {
        List<VproNavbar> list = getCategories();
        List<CategoryNode> navs = new ArrayList<CategoryNode>();
        for(int i = 0; i < list.size(); i++)
        {
            // 顶层目录
            if (list.get(i).getNavPid() == 0)
            {
                System.out.println(list.get(i).toString());
                // 转换成专属格式
                CategoryNode categoryNode = DtoUtil.vproNavbar2CategoryNode(list.get(i));
                // 收集该顶层目录下的子目录
                CategoryNode nav = getSubCategory(categoryNode);
                navs.add(nav);
//                list.remove(i);
            }
        }
//        return navs;
        return new Result(navs);
    }
    // 给他一个默认参数，如果为空就放一个空List对象。
    public CategoryNode getSubCategory(CategoryNode categoryNode) {
        List<CategoryNode> subs = new ArrayList<CategoryNode>();
        return getSubCategory(categoryNode, subs);
    }

    /**
     * 迭代整个目录列表，判断传过来的目录是否
     * @param mainNav 主目录
     * @param subNavs 传入的ID所指目录的子目录list()
     * @return 子目录CategoryNode
     */
    public CategoryNode getSubCategory(CategoryNode mainNav, List<CategoryNode> subNavs) {
        for(int i = 0; i < list.size(); i++)
        {
            // 判断次级目录的父目录是否属于传来的目录
            if (list.get(i).getNavPid().equals(mainNav.getNavId()))
            {
                // 次级目录转换格式
                CategoryNode sub = DtoUtil.vproNavbar2CategoryNode(list.get(i));
                // 子目录的迭代结果，寻找该目录下是否还有子目录
                sub = this.getSubCategory(sub);
                subNavs.add(sub);
//                list.remove(i);
            }
        }
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
     */
    public List<Integer> getSubIdFromCategory(Integer navId, List<VproNavbar> list, List<Integer> idList) {
        VproNavbar vproNavbar = getCategory(navId);
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

    public int addCategory(VproNavbar vproNavbar)
    {
        return  vproNavbarMapper.insert(vproNavbar);
    }
    public int removeCategory(Integer id)
    {
        return vproNavbarMapper.deleteByPrimaryKey(id);
    }
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
