package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.CourseDaoAbstract;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component("courseByDB")
public class CourseDaoByDBImpl extends CourseDaoAbstract {
    @Autowired
    CoursesMapper coursesMapper;
    @Autowired
    JedisClient jedisClient;

    @Value("${indexNavPrefix}")
    String indexNavPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

    @Value("${indexCoursesPrefix}")
    String indexCoursesPrefix;

    @Override
    public Courses getCourse(String courseId) {
        return null;
    }

    @Override
    public List<Courses> getCoursesByPid(List<Integer> ids) {
        return null;
    }

    @Override
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids) {
        return null;
    }

    @Override
    public List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException{
        try {
            List<Courses> indexCourses = coursesMapper.getIndexCoursesInfo(navIds);
//            String str = JSON.toJSONString(indexCourses);
//            jedisClient.set(indexNavPrefix + navPid, str);
//            jedisClient.zadd(indexNavPrefix + expiredSuffix, JedisUtil.expiredTimeStamp(), indexNavPrefix + navPid);
            return indexCourses;
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }

}
