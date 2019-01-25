package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component("courseByDB")
public class CourseDaoByDBImpl implements CourseDao {
    @Autowired
    CoursesMapper coursesMapper;
    @Autowired
    JedisClient jedisClient;

    @Value("${indexNavPrefix}")
    String indexNavPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

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
        List<Courses> indexCourses = coursesMapper.getIndexCoursesInfo(navIds);
        if (indexCourses.size() == 0) throw new CourseException("get index courses failed! navIds: " + navIds);
        String str = JSON.toJSONString(indexCourses);
        jedisClient.set(indexNavPrefix + navPid, str);
        jedisClient.zadd(indexNavPrefix + expiredSuffix, (double)(System.currentTimeMillis() / 1000), indexNavPrefix + navPid);
        return indexCourses;
    }

}
