package com.mark.manager.daoImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.pojo.VproCoursesLessonList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class LessonDaoByRedisImpl implements LessonDao {
    private static final Logger logger = LoggerFactory.getLogger(LessonDaoByRedisImpl.class);
    @Autowired
    JedisClient jedisClient;

    private static final String lessonPrefix = "lesson";
    private static final String courseLessonsPrefix = "courseLessons";

    private Jedis getRedis() {
        JedisPool jedisPool = jedisClient.getJedisPool();
        return jedisPool.getResource();
    }

    /*@Override
    public List<VproCoursesLessonList> getLessonsList() {
        List<VproCoursesLessonList> list = new ArrayList<VproCoursesLessonList>();
        Set<String> keys = jedisClient.keys("lesson*");
        Jedis jedis = getRedis();
        Pipeline p = jedis.pipelined();
        if (keys.size() > 0) {
            List<Response<Map<String, String>>> resList = new ArrayList<Response<Map<String, String>>>();
            for(String key : keys) {
                resList.add(p.hgetAll(key));
            }
            p.sync();
            try {
                for(Response<Map<String, String>> res : resList) {
                    VproCoursesLessonList vproCoursesLessonList = BeanUtil.map2bean(res.get(), VproCoursesLessonList.class);
                    list.add(vproCoursesLessonList);
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            jedis.close();
        }
        return list;
    }*/
    public void cacheLessonsList(List<VproCoursesLessonList> list) throws IntrospectionException {
        logger.info("将lessons插入redis");
        Jedis jedis = getRedis();
        Pipeline p = jedis.pipelined();
        for(VproCoursesLessonList vproCoursesLessonList : list) {
            Map<String, String> lesson = BeanUtil.bean2map(vproCoursesLessonList);
            p.hmset(lessonPrefix + lesson.get("lessonId"), lesson);
        }
        p.sync();
        jedis.close();
    }

    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) {
        List<VproCoursesLessonList> list = new ArrayList<VproCoursesLessonList>();
        String key = courseLessonsPrefix + String.valueOf(courseId);
        if (jedisClient.exists(key)) {
            String lessons = jedisClient.get(key);
        }

        return list;
    }

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) throws IllegalAccessException, IntrospectionException, InstantiationException {
        Map<String, String> res = jedisClient.hgetAll(lessonPrefix + String.valueOf(lessonId));
        if (res != null) {
            return BeanUtil.map2bean(res, VproCoursesLessonList.class);
        }
        return null;
    }
}
