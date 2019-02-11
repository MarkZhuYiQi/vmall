package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.CourseDaoAbstract;
import com.mark.manager.dto.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("courseByRedis")
public class CourseDaoByRedisImpl extends CourseDaoAbstract {
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
}
