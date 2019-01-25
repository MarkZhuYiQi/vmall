package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("courseByRedis")
public class CourseDaoByRedisImpl implements CourseDao {
    @Value("${coursePrefix}")
    private String coursePrefix;
    @Autowired
    JedisClient jedisClient;
    @Override
    public Courses getCourse(String courseId) {
//        Map<String, String> course = jedisClient.hgetAll(coursePrefix + courseId);
        String course = jedisClient.get("courseTest");
        JSONObject jsonObject = JSON.parseObject(course);
        return JSON.toJavaObject(jsonObject, Courses.class);
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
    public List<Courses> getIndexCoursesInfo(List<Integer> navIds) throws CourseException {
        throw new CourseException("get index courses from redis failed!");
    }
}
