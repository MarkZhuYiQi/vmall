package com.mark.manager.daoImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.CourseDaoAbstract;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.mapper.VproCoursesTempDetailMapper;
import com.mark.manager.pojo.VproCoursesTempDetail;
import com.mark.manager.pojo.VproCoursesTempDetailExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("courseByDB")
public class CourseDaoByDBImpl extends CourseDaoAbstract {
    @Autowired
    CoursesMapper coursesMapper;

    @Autowired
    VproCoursesTempDetailMapper vproCoursesTempDetailMapper;

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

    @Value("${coursesClicksSummary}")
    String coursesClicksSummary;


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

    @Override
    public PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException {
        try{
            PageHelper.startPage(currentPage, pageSize);
            List<Courses> courses = coursesMapper.getCoursesInfoForCatalog(ids);
            PageInfo page = new PageInfo(courses);
            jedisClient.hset(coursesForCatalogPrefix + String.valueOf(navId), String.valueOf(currentPage), JSON.toJSONString(page));
            // 过期时间是基于整个navId下的，而缓存是基于页码的，一个页码过期，会刷新整个navId的日期，所以暂时在过期后直接删除整个缓存
            jedisClient.zadd(coursesForCatalogPrefix + expiredSuffix, JedisUtil.expiredTimeStamp(), coursesForCatalogPrefix + String.valueOf(navId));
            return page;
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }

    @Override
    public Courses getCourseForDetail(Integer courseId) throws CourseException {
        try {
            Courses course = coursesMapper.getCourseForDetail(courseId);
            Double clickNum = jedisClient.zscore(coursesClicksSummary, String.valueOf(courseId));
            if (clickNum != null && clickNum >= 0) course.getVproCoursesTempDetail().setCourseClickNum(clickNum.intValue());
            // 直接课程转成json了
            jedisClient.set(coursesDetailPrefix + String.valueOf(courseId), BeanUtil.parseObjToJson(course));
            Double expiredTime = JedisUtil.expiredTimeStamp();
            // 到期自动删除
            jedisClient.expireAt(coursesDetailPrefix + courseId, expiredTime.longValue());
            // 生成一个课程信息过期set，备用
            jedisClient.zadd(coursesDetailPrefix + expiredSuffix, Double.valueOf(expiredTime), String.valueOf(courseId));
            return course;
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }

    /**
     * 通过获得导航id下的所有课程的点击数统计出最受欢迎的课程加入推荐集合。
     * @param ids
     * @return
     */
    @Override
    public List<Integer> getTopClicksForNavSpecified(List<Integer> ids) throws CourseException {
        try {
            VproCoursesTempDetailExample vproCoursesTempDetailExample = new VproCoursesTempDetailExample();
            vproCoursesTempDetailExample.createCriteria().andCoursePidIn(ids);
            vproCoursesTempDetailExample.setOrderByClause("course_clickNum desc");
            List<VproCoursesTempDetail> list = vproCoursesTempDetailMapper.selectByExample(vproCoursesTempDetailExample);
            List<Integer> coursesId = new ArrayList<>();
            // 没有得到，一个课程也没有，就返回空list
            if (list == null || list.size() == 0) return coursesId;
            List<VproCoursesTempDetail> topList = new ArrayList<>();
            System.arraycopy(list.toArray(), 0, topList.toArray(), 0, 12);
            for (VproCoursesTempDetail v : topList) {
                coursesId.add(v.getCourseId());
            }
            return coursesId;
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }
}
