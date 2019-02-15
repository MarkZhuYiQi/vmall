package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.mark.common.exception.LessonException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.pojo.VproCoursesLessonListExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

@Repository("lessonDaoByDB")
public class LessonDaoByDBImpl implements LessonDao {
    private final static Logger logger = LoggerFactory.getLogger(LessonDaoByDBImpl.class);

    @Value("${lessonsListPrefix}")
    String lessonsListPrefix;

    @Value("${lessonPrefix}")
    String lessonPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

    @Autowired
    VproCoursesLessonListMapper vproCoursesLessonListMapper;
    @Autowired
    JedisClient jedisClient;

    private Jedis getRedis() {
        JedisPool jedisPool = jedisClient.getJedisPool();
        return jedisPool.getResource();
    }
    /**
     * 将课程列表每一课用hash表形式缓存（管道式插入）
     * @param list
     */
    private void cacheLessonsList(List<VproCoursesLessonList> list) {
        logger.info("将lessons插入redis");
        Jedis jedis = getRedis();
        Pipeline p = jedis.pipelined();
        for(VproCoursesLessonList vproCoursesLessonList : list) {
            Map<String, String> lesson = BeanUtil.beanToMap(vproCoursesLessonList, VproCoursesLessonList.class);
            p.hmset(lessonPrefix + lesson.get("lessonId"), lesson);
        }
        p.sync();
        jedis.close();
    }

    /**
     * 课程列表以hash表格式缓存
     * @param list
     * @param courseId
     */
    private void cacheLessonsListHash(List<VproCoursesLessonList> list, Integer courseId) {
       Jedis jedis = getRedis();
       Pipeline p = jedis.pipelined();
        for (VproCoursesLessonList vproCoursesLessonList : list) {
            p.hset(lessonsListPrefix + String.valueOf(courseId), String.valueOf(vproCoursesLessonList.getLessonId()), BeanUtil.parseObjToJson(vproCoursesLessonList));
        }
        p.sync();
        jedis.close();
    }

    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException {
        try {
            VproCoursesLessonListExample vproCoursesLessonListExample = new VproCoursesLessonListExample();
            vproCoursesLessonListExample.setOrderByClause("lesson_lid");
            vproCoursesLessonListExample.createCriteria().andLessonCourseIdEqualTo(courseId);
            List<VproCoursesLessonList> list =  vproCoursesLessonListMapper.selectByExample(vproCoursesLessonListExample);
//            cacheLessonsList(list);
//            cacheLessonsListHash(list, courseId);
            Double expiredTime = JedisUtil.expiredTimeStamp();
            jedisClient.set(lessonsListPrefix + String.valueOf(courseId), JSON.toJSONString(list));
            System.out.println("before： " + jedisClient.get(lessonsListPrefix + String.valueOf(courseId)));
            System.out.println(expiredTime.longValue());
            jedisClient.expireAt(lessonsListPrefix + String.valueOf(courseId), expiredTime.longValue());
            System.out.println("after: " + jedisClient.get(lessonsListPrefix + String.valueOf(courseId)));
            jedisClient.zadd(lessonsListPrefix + expiredSuffix, expiredTime, String.valueOf(courseId));
            return list;
        } catch (Exception e) {
            throw new LessonException(e.getMessage());
        }
    }

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) throws LessonException {
        try {
            return vproCoursesLessonListMapper.selectByPrimaryKey(lessonId);
        } catch (Exception e) {
            throw new LessonException(e.getMessage());
        }
    }

}
