package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.jwt.JwtUtil;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.common.util.LogUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.CourseDaoAbstract;
import com.mark.manager.dto.Courses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component("courseByRedis")
public class CourseDaoByRedisImpl extends CourseDaoAbstract {
    private static final Logger logger = LoggerFactory.getLogger(CourseDaoByRedisImpl.class);
    @Value("${coursePrefix}")
    private String coursePrefix;
    @Autowired
    JedisClient jedisClient;

    @Value("${indexNavPrefix}")
    String indexNavPrefix;

    @Value("${expiredSuffix}")
    String expiredSuffix;

    @Value("${indexCoursesPrefix}")
    String indexCoursesPrefix;

    @Value("${coursesForCatalogPrefix}")
    String coursesForCatalogPrefix;

    @Value("${coursesDetailPrefix}")
    String coursesDetailPrefix;

    @Value("${recCoursePrefix}")
    String recCoursePrefix;

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

//    /**
//     *
//     * @param navPid 导航的ID
//     * @param navIds 导航ID下所属于该ID的子ID集合
//     * @return
//     * @throws CourseException
//     */
//    @Override
//    public List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException {
//        Double timeStamp = jedisClient.zscore(indexNavPrefix + expiredSuffix, indexNavPrefix + navPid);
//        if (timeStamp == null || JedisUtil.isExpired(timeStamp)) throw new CourseException("index courses in redis has been expired!");
//        String str = jedisClient.get(indexNavPrefix + navPid);
//        if (StringUtils.isEmpty(str)) throw new CourseException("get index courses from redis failed!");
//        return JSON.parseArray(str, Courses.class);
//    }

    /**
     * 判断是否存在并且没有过期
     * @param navId 导航id
     * @return boolean
     */
    @Override
    public boolean indexCoursesIsExisted(Integer navId) {
        if (jedisClient.exists(indexCoursesPrefix + String.valueOf(navId))) {
            Double timeStamp = jedisClient.zscore(indexCoursesPrefix + expiredSuffix, indexCoursesPrefix + navId);
            return (timeStamp != null) && (!JedisUtil.isExpired(timeStamp));
        }
        return false;
    }
    public void setIndexCoursesCache(Integer indexNavId, Map<Integer, List<Courses>> indexCoursesCache) {
        jedisClient.set(indexCoursesPrefix + String.valueOf(indexNavId), JSON.toJSONString(indexCoursesCache));
        jedisClient.zadd(indexCoursesPrefix + expiredSuffix, JedisUtil.expiredTimeStamp(), indexCoursesPrefix + String.valueOf(indexNavId));
    }

    @Override
    public Map<Integer, List<Courses>> getIndexCoursesCache(Integer indexNavId) throws CourseException {
        String str = jedisClient.get(indexCoursesPrefix + String.valueOf(indexNavId));
        if (StringUtils.isEmpty(str)) throw new CourseException("indexCoursesCache stored in redis is empty", CourseConstant.GET_INDEX_COURSES_INFO_FROM_REDIS_FAILED);
        return JSON.parseObject(str, Map.class);
    }

    @Override
    public Set<String> checkIndexCourseCache() throws CourseException {
        String expiredKey = indexCoursesPrefix + expiredSuffix;
        System.out.println(expiredKey);
        if (jedisClient.exists(expiredKey)) {
//            Set<String> expiredSet = jedisClient.zRangeByScore(expiredKey, (double)0, (double)System.currentTimeMillis());
            Set<String> expiredSet = jedisClient.zRange(expiredKey, (long)0, (long)-1);
            if (expiredSet.size() != 0) return expiredSet;
            throw new CourseException(indexCoursesPrefix + " cache is null");
        }
        throw new CourseException(indexCoursesPrefix + " cache does not exist");
    }

    @Override
    public PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException {

        // 如果缓存过期，就把整个hash表全删除，（所有页码的缓存都删除）
        String expiredKey = coursesForCatalogPrefix + expiredSuffix;
        if (jedisClient.exists(expiredKey)) {
            Double expiredTime = jedisClient.zscore(expiredKey, coursesForCatalogPrefix + String.valueOf(navId));
            if (expiredTime == null || JedisUtil.isExpired(expiredTime)) {
                jedisClient.del(coursesForCatalogPrefix + String.valueOf(navId));
            }
        }

        String str = jedisClient.hget(coursesForCatalogPrefix + navId, String.valueOf(currentPage));
        if (StringUtils.isEmpty(str)) {
            String err = String.format("%s->%s: courses for catalog %s does not exist", LogUtil.getObjectName(), LogUtil.funcName(), navId);
            throw new CourseException(err);
        }
        return JSON.parseObject(str, PageInfo.class);
    }

    @Override
    public Courses getCourseForDetail(Integer courseId) throws CourseException {
        String courseKey = coursesDetailPrefix + String.valueOf(courseId);
        if (jedisClient.exists(courseKey)) {
            // 课程信息过期不仅设置了set，而且设置了自动过期，所以可以不用这个判断
            Double expiredTime = jedisClient.zscore(coursesDetailPrefix + expiredSuffix, String.valueOf(courseId));
            logger.info(String.valueOf(expiredTime));
            if (expiredTime != null && !JedisUtil.isExpired(expiredTime)) {
                String course = jedisClient.get(courseKey);
                return BeanUtil.parseJsonToObj(course, Courses.class);
            }
        }
        throw new CourseException("course cache does not exist or expired, courseId: " + courseId);
    }

    /**
     * recCourses是设置到set中的，如果没有，那么这个set就是空的
     * 程序不知道这个是没有推荐，还是没有其他可推荐内容。
     * 所以需要一个sortedset，来记录这个set的过期时间，如果过期时间超过现在的时间，说明是被删除了，需要重新生成
     * 如果过期时间比当前时间晚，说明已经生成过。
     * @param navId
     * @param coursesId
     * @return
     */
    @Override
    public Boolean setRecCoursesIdInRedis(Integer navId, List<Integer> coursesId) {
        String[] courses;
        String setKey = recCoursePrefix + String.valueOf(navId);
        Double expiredTimeStamp = JedisUtil.expiredTimeStamp();
        if (coursesId.size() > 0) {
            courses = new String[coursesId.size()];
            for (int c = 0; c < coursesId.size(); c++) {
                courses[c] = String.valueOf(coursesId.get(c));
            }
            logger.info("waiting to write: " + courses[0]);
            // 添加到set中
            jedisClient.sadd(setKey, courses);
            System.out.println(expiredTimeStamp);
            System.out.println(expiredTimeStamp.longValue());
            jedisClient.expireAt(recCoursePrefix + String.valueOf(navId), expiredTimeStamp.longValue());
        }
//        Collections.addAll(courses, coursesId.toArray(new String[coursesId.size()]));
        // set过期时间通过一个sortedset进行设置
        jedisClient.zadd(recCoursePrefix + expiredSuffix, expiredTimeStamp, String.valueOf(navId));
        return true;
    }

    /**
     * 根据导航id去redis取rec
     * @param navId
     * @return
     * @throws CourseException
     */
    @Override
    public List<Integer> getRandomRecCoursesId(Integer navId) throws CourseException {
        String expiredKey = recCoursePrefix + expiredSuffix;
        Double expiredTime = jedisClient.zscore(expiredKey, String.valueOf(navId));
        // 如果过期时间不存在那么说明从未生成过推荐
        if (expiredTime == null || expiredTime == 0)
            throw new CourseException("recommend course not generate yet. navId: " + String.valueOf(navId), CourseConstant.REC_COURSE_NOT_GENERATE);
        // 如果已经过期，则重新生成。
        if (JedisUtil.isExpired(expiredTime)) {
            throw new CourseException("recommend course is expired! navId: " + navId, CourseConstant.REC_COURSE_EXPIRED);
        }
        List<Integer> res = new ArrayList<Integer>();
        List<String> coursesId = jedisClient.srandmember(recCoursePrefix + String.valueOf(navId), 3);
        // 拿到空就为空
        if (coursesId.size() == 0 || coursesId == null) return res;
        for (String c : coursesId) {
            res.add(Integer.parseInt(c));
        }

        return res;
    }
}
