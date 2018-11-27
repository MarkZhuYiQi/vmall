package com.mark.manager.daoImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.pojo.VproCoursesLessonList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class lessonDaoByRedisImpl implements LessonDao {
    @Autowired
    JedisClient jedisClient;

    @Override
    public List<VproCoursesLessonList> getLessonsList() {
        Set<String> keys = jedisClient.keys("lesson*");
        System.out.println(keys);
        return null;
    }
}
