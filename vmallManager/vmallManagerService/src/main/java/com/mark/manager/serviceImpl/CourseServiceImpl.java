package com.mark.manager.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.UidUtil;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.mapper.VproCoursesContentMapper;
import com.mark.manager.mapper.VproCoursesMapper;
import com.mark.manager.pojo.VproCourses;
import com.mark.manager.pojo.VproCoursesContent;
import com.mark.manager.pojo.VproCoursesContentExample;
import com.mark.manager.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
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
    public Courses updateCourse(CourseUpdate courseUpdate) {
        try {
            Map<String, String> map = BeanUtil.bean2map(courseUpdate);
            Integer flag = 0;
            for(Map.Entry<String, String> m : map.entrySet()) {
                if (!m.getValue().equals("") && !m.getValue().equals("-1")) flag = 1;
            }
            Integer res = 0;
            if (flag != 0) {
                VproCourses vproCourses = DtoUtil.courseUpdate2VproCoueses(courseUpdate);
                res = vproCoursesMapper.updateByPrimaryKeySelective(vproCourses);
                if (res == 0) {
                    logger.warn("课程信息更新失败");
                }
                logger.info("课程信息更新成功");
                res = 0;
                if (courseUpdate.getCourseContent() != null && courseUpdate.getCourseContent().length() != 0 ) {
                    // 构建详细描述对象
                    VproCoursesContent vproCoursesContent = new VproCoursesContent();
                    vproCoursesContent.setCourseId(courseUpdate.getCourseId());
                    // 查询数据库有没有这个对象
                    VproCoursesContentExample vproCoursesContentExample = new VproCoursesContentExample();
                    vproCoursesContentExample.createCriteria().andCourseIdEqualTo(courseUpdate.getCourseId());
                    Long count = vproCoursesContentMapper.countByExample(vproCoursesContentExample);

                    if (count > 0) {
                        res = vproCoursesContentMapper.updateByPrimaryKey(vproCoursesContent);
                        if (res > 0) logger.info("课程详细描述更新成功");
                        logger.info("课程详细描述更新失败");
                        res = 0;
                    } else {
                        res = vproCoursesContentMapper.insert(vproCoursesContent);
                        if (res > 0) logger.info("课程详细描述插入成功");
                        logger.info("课程详细描述插入成功");
                    }
                }
                // 这里应该发送一个更新信号给消息队列或者程序，去处理缓存
                return getCourse(courseUpdate.getCourseId());
            } else {
                logger.warn("课程更新内容为空，失败");
            }
            return null;
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Courses createCourse(Courses courses) {
        VproCourses vproCourses = new VproCourses();
        vproCourses = DtoUtil.courses2VproCourses(courses);
        Integer courseId = Integer.parseInt(String.valueOf(UidUtil.getUid(jedisClient)));
        System.out.println(courseId);
        vproCourses.setCourseId(courseId);
        System.out.println(vproCourses.getCourseId());
        Integer id = vproCoursesMapper.insertSelective(vproCourses);
        Courses c = getCourse(id);
        System.out.println(c.toString());
        return c;
    }
}
