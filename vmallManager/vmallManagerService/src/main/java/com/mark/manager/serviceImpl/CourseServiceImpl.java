package com.mark.manager.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CartException;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.UidUtil;
import com.mark.manager.bo.Result;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.mapper.VproCoursesContentMapper;
import com.mark.manager.mapper.VproCoursesMapper;
import com.mark.manager.pojo.VproCourses;
import com.mark.manager.pojo.VproCoursesContent;
import com.mark.manager.pojo.VproCoursesContentExample;
import com.mark.manager.service.CartService;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    VproCoursesMapper vproCoursesMapper;
    @Autowired
    CoursesMapper coursesMapper;
    @Autowired
    VproCoursesContentMapper vproCoursesContentMapper;
    @Autowired
    JedisClient jedisClient;
    @Autowired
    @Qualifier("courseDao")
    CourseDao courseDao;
    @Autowired
    CartService cartService;
    @Autowired
    CategoryService categoryService;

    @Override
    public Courses getCourse(Integer courseId) {
        Map<String, String> idCriteria = new HashMap<String, String>();
        idCriteria.put("courseId", String.valueOf(courseId));
        List<Courses> course = coursesMapper.getCourses(idCriteria);
        if (course.size() > 0) {
            return course.get(0);
        }
        return null;
    }

    @Override
    public List<Courses> getCoursesByPid(List<Integer> ids)
    {
        String pids = getCoursePidCriteria(ids);
        List<Courses> courses = coursesMapper.getCoursesByPid(pids);
        return courses;
    }
    private String getCoursePidCriteria(List<Integer> ids)
    {
        StringBuilder pids = new StringBuilder();
        for(int i = ids.size() - 1; i >= 0; i--)
        {
            pids.append(ids.get(i).toString());
            if (i != 0) pids.append(",");
        }
        return pids.toString();
    }
    @Override
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids)
    {
        PageHelper.startPage(currentPage, pageSize);
        List<Courses> courses = coursesMapper.getCoursesByPid(getCoursePidCriteria(ids));
        PageInfo page = new PageInfo(courses);
        return page;
    }

    @Override
    public Courses updateCourse(CourseUpdate courseUpdate) throws CourseException {
        try {
            Map<String, String> map = BeanUtil.bean2map(courseUpdate);
            System.out.println(map);
            Boolean result = updateCourseContent(courseUpdate);
            Integer flag = 0;
            // 判断是否有需要更新的信息
            for(Map.Entry<String, String> m : map.entrySet()) {
                if (!m.getValue().equals("") && !m.getValue().equals("-1") && !m.getKey().equals("courseId") && !m.getKey().equals("courseContent") ) {
                    flag = 1;
                    break;
                }
            }
            Integer res = 0;
            if (flag != 0) {
                System.out.println("updateCourse_coursesUpdate: " + courseUpdate.toString());
                // 开始更新
                VproCourses vproCourses = DtoUtil.courseUpdate2VproCoueses(courseUpdate);
                System.out.println("updateCourse_vproCourses: " + vproCourses.toString());
                res = vproCoursesMapper.updateByPrimaryKeySelective(vproCourses);
                if (res == 0) {
                    logger.warn("课程信息更新失败");
                    throw new CourseException("课程信息更新失败", CourseConstant.UPDATE_COURSE_WITHOUT_AFFECTED_ROWS);
                }
                logger.info("课程信息更新成功");
                // 这里应该发送一个更新信号给消息队列或者程序，去处理缓存
            }
            // 主内容或者content更新后返回新内容
            if (res > 0 || result) {
                return getCourse(courseUpdate.getCourseId());
            } else {
                logger.warn("课程更新内容为空，失败");
                throw new CourseException("课程更新内容为空", CourseConstant.UPDATE_COURSE_WITHOUT_INFO);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Boolean updateCourseContent(CourseUpdate courseUpdate) throws CourseException {
        // 以下是更新课程的概述
        if (courseUpdate.getCourseContent() != null && courseUpdate.getCourseContent().length() != 0 ) {
            // 构建详细描述对象
            VproCoursesContent vproCoursesContent = new VproCoursesContent();
            vproCoursesContent.setCourseId(courseUpdate.getCourseId());
            vproCoursesContent.setCourseContent(courseUpdate.getCourseContent().toString());
            // 查询数据库有没有这个对象
            VproCoursesContentExample vproCoursesContentExample = new VproCoursesContentExample();
            vproCoursesContentExample.createCriteria().andCourseIdEqualTo(courseUpdate.getCourseId());
            Long count = vproCoursesContentMapper.countByExample(vproCoursesContentExample);
            Integer res = 0;
            if (count > 0) {
                // 找到这个课程的概述，就给她更新
                res = vproCoursesContentMapper.updateByPrimaryKey(vproCoursesContent);
                if (res == 0) {
                    logger.warn("课程详细描述更新失败");
                    throw new CourseException("课程详细描述更新失败", CourseConstant.UPDATE_COURSE_CONTENT_FAILURE);
                }
                logger.info("课程详细描述更新成功");
                res = 0;
            } else {
                // 没找到就给他新增
                res = vproCoursesContentMapper.insert(vproCoursesContent);
                if (res == 0) {
                    logger.warn("课程详细描述插入失败");
                    throw new CourseException("课程详细描述插入失败", CourseConstant.INSERT_COURSE_CONTENT_FAILURE);
                }
                logger.info("课程详细描述插入成功");
            }
            return Boolean.valueOf(String.valueOf(res));
        }
        return false;
    }

    @Override
    public Courses createCourse(Courses courses) throws CourseException {
        VproCourses vproCourses = new VproCourses();
        vproCourses = DtoUtil.courses2VproCourses(courses);
        Integer courseId = Integer.parseInt(String.valueOf(UidUtil.getUid(jedisClient)));
        vproCourses.setCourseId(courseId);
        Integer res = vproCoursesMapper.insertSelective(vproCourses);
        if (res > 0) {
            return getCourse(courseId);
        } else {
            throw new CourseException("create course failed", CourseConstant.INSERT_COURSE_AUTHOR_FAILURE);
        }
    }

    @Override
    public Map<Integer, List<Courses>> getIndexCoursesInfo(Integer indexNavId, Map<Integer, List<Integer>> navIds) throws CourseException {
        long time = System.currentTimeMillis();
        Map<Integer, List<Courses>> indexCourses = new HashMap<Integer, List<Courses>>();
//        logger.info("navIds: " + navIds);
        for(Map.Entry<Integer, List<Integer>> e : navIds.entrySet()) {
//            logger.info("find indexCourses below navId: " + e.getValue());
            if (e.getValue().size() == 0) continue;
            List<Courses> courses = courseDao.getIndexCoursesInfo(e.getKey(), e.getValue());
            indexCourses.put(e.getKey(), courses);
        }
        courseDao.setIndexCoursesCache(indexNavId, indexCourses);
        System.out.println("得到展示页课程需要时间：" + String.valueOf(System.currentTimeMillis() - time) + "ms");
        return indexCourses;
    }

    @Override
    public Map<Integer, List<Courses>> getIndexCoursesInfoCache(Integer indexNavId) throws CourseException {
        return courseDao.getIndexCoursesCache(indexNavId);
    }

    @Override
    public boolean indexCoursesIsExisted(Integer indexNavId) {
        return courseDao.indexCoursesIsExisted(indexNavId);
    }

    @Override
    public PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException {
        return courseDao.getCoursesForCatalog(navId, currentPage, pageSize, ids);
    }

    @Override
    public Courses getCourseForDetail(Integer courseId) throws CourseException {
        return courseDao.getCourseForDetail(courseId);
    }

    @Override
    public List<String> checkCourses(List<String> coursesId) throws CartException {
        try {
            List<String> res = new ArrayList<>();
            List<Courses> list = cartService.getCourseDetailInCart(coursesId);
            for (Courses c : list) {
                if (c.getCourseStatus() == 1) res.add(c.getCourseId());
            }
            return res;
        } catch (CartException e) {
            throw new CartException(e.getMsg(), e.getCode());
        }
    }

    @Override
    public List<Courses> getRecCoursesByNavIds(Integer navId) throws CourseException {
        try {
            return getRecCourses(navId);
        } catch (CourseException e) {
            logger.info("trying...");
            try {
                genRecCoursesByNavId(navId);
                return getRecCourses(navId);
            } catch (CategoryException eca) {
                throw new CourseException(eca.getMsg(), eca.getCode());
            } catch (CourseException eco) {
                throw new CourseException(eco.getMsg(), eco.getCode());
            }
        }
    }

    private List<Courses> getRecCourses(Integer navId) throws CourseException {
        List<Integer> coursesId = courseDao.getRandomRecCoursesId(navId);
        List<Courses> list = new ArrayList<>();
        for (Integer courseId : coursesId) {
            list.add(getCourseForDetail(courseId));
        }
        return list;
    }

    /**
     * 生成推荐课程
     * 首先从导航中获取旗下的导航id
     * 其次根据子导航id获取这些导航下的点击率排名前12的课程id
     * 最后将这些id放到redis中
     * @param navId
     * @throws CategoryException
     * @throws CourseException
     */
    @Override
    public void genRecCoursesByNavId(Integer navId) throws CategoryException, CourseException {
        // 根据navId，获得所有子导航id
        List<Integer> subNavIds = categoryService.getSubIdFromCategory(navId);
        // 根据子导航id，去数据库获得子导航中前12名课程id
        List<Integer> coursesId = courseDao.getTopClicksForNavSpecified(subNavIds);
        logger.info(coursesId.toString());
        courseDao.setRecCoursesIdInRedis(navId, coursesId);
    }

    @Override
    public Long test() {
        return System.currentTimeMillis();
    }

}
