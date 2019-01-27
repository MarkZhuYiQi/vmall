package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Component("courseByRedis")
public class CourseDaoByRedisImpl implements CourseDao {
    @Value("${coursePrefix}")
    private String coursePrefix;
    @Autowired
    JedisClient jedisClient;

    @Value("${indexNavPrefix}")
    String indexNavPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

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
    public List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException {
        Double timeStamp = jedisClient.zscore(indexNavPrefix + expiredSuffix, indexNavPrefix + navPid);
        if (timeStamp == null || JedisUtil.isExpired(timeStamp)) throw new CourseException("index courses in redis has been expired!");
        String str = jedisClient.get(indexNavPrefix + navPid);
        if (StringUtils.isEmpty(str)) throw new CourseException("get index courses from redis failed!");
        return JSON.parseArray(str, Courses.class);
    }
}
