package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mark.common.exception.LessonException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.pojo.VproCoursesLessonList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.beans.IntrospectionException;
import java.util.*;

@Repository("lessonDaoByRedis")
public class LessonDaoByRedisImpl implements LessonDao {
    private static final Logger logger = LoggerFactory.getLogger(LessonDaoByRedisImpl.class);
    @Autowired
    JedisClient jedisClient;

    @Value("${lessonsListPrefix}")
    String lessonsListPrefix;

    @Value("${lessonPrefix}")
    String lessonPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

    /**
     * 获得课程列表缓存，列表用hash格式存储，{lessonId: lessonInfo(json), lessonId2: lessonInfo2, ...}
     * @param courseId
     * @return
     */
    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException {
        String key = lessonsListPrefix + String.valueOf(courseId);
        Double timestamp = jedisClient.zscore(lessonsListPrefix + expiredSuffix, String.valueOf(courseId));
        if (jedisClient.exists(key) && timestamp != null && !JedisUtil.isExpired(timestamp)) {
            String json = jedisClient.get(key);
            return JSON.parseArray(json, VproCoursesLessonList.class);
        }
        throw new LessonException("get lessonsList cache failed");
    }

    /**
     * redis获得单个课程项目 hash形式
     * @param lessonId
     * @return
     */
    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) {
        Map<String, String> res = jedisClient.hgetAll(lessonPrefix + String.valueOf(lessonId));
        if (res != null) {
            return BeanUtil.mapToBean(res, VproCoursesLessonList.class);
        }
        return null;
    }
}
