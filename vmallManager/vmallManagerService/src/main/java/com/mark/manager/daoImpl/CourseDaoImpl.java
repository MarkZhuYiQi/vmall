package com.mark.manager.daoImpl;

import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.util.LogUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dao.CourseDaoAbstract;
import com.mark.manager.dto.Courses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("courseDao")
public class CourseDaoImpl extends CourseDaoAbstract {
    private final static Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
    @Autowired
    @Qualifier("courseByDB")
    CourseDao courseDaoByDB;
    @Autowired
    @Qualifier("courseByRedis")
    CourseDao courseDaoByRedis;

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
    public List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException {
        String err;
        List<Courses> indexCourses;
        try {
            indexCourses = courseDaoByDB.getIndexCoursesInfo(navPid, navIds);
        } catch (CourseException ex) {
            err = String.format("%s->%s: %s, navPid: %d, navIds: %s", LogUtil.getObjectName(), LogUtil.funcName(), ex.getMsg(), navPid, navIds.toString());
            logger.info(err);
            throw new CourseException("Get index courses failed! check the params: " + navIds, CourseConstant.GET_INDEX_COURSES_INFO_FAILED);
        }
        return indexCourses;
    }

    @Override
    public boolean indexCoursesIsExisted(Integer indexNavId) {
        return courseDaoByRedis.indexCoursesIsExisted(indexNavId);
    }

    @Override
    public Map<Integer, List<Courses>> getIndexCoursesCache(Integer indexNavId) throws CourseException {
        return courseDaoByRedis.getIndexCoursesCache(indexNavId);
    }

    @Override
    public void setIndexCoursesCache(Integer indexNavId, Map<Integer, List<Courses>> indexCoursesCache) {
        courseDaoByRedis.setIndexCoursesCache(indexNavId, indexCoursesCache);
    }

    @Override
    public PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException {
        try {
            return courseDaoByRedis.getCoursesForCatalog(navId, currentPage, pageSize, ids);
        } catch (CourseException e) {
            logger.info(e.getMsg());
            try {
                return courseDaoByDB.getCoursesForCatalog(navId, currentPage, pageSize, ids);
            } catch (CourseException ec) {
                throw new CourseException("get courses for catalog " + navId + " faild!" + ec.getMessage(), CourseConstant.GET_COURSES_FOR_CATALOG);
            }
        }
    }

    @Override
    public Courses getCourseForDetail(Integer courseId) throws CourseException {
        try{
            return courseDaoByRedis.getCourseForDetail(courseId);
        } catch (CourseException e) {
            logger.warn("get courses detail info from cache failed. {}->{}: {}", LogUtil.getObjectName(), LogUtil.funcName(), e.getMsg());
            try {
                return courseDaoByDB.getCourseForDetail(courseId);
            } catch (CourseException ec) {
                logger.warn("get courses detail info from DB failed. {}->{}: {}", LogUtil.getObjectName(), LogUtil.funcName(), ec.getMessage());
                throw new CourseException("get courses info failed!", CourseConstant.GET_COURSES_DETAIL_FAILED);
            }
        }
    }

    /**
     * 2. 根据子导航id，去数据库获得子导航中前12名课程id
     * @param ids
     * @return 返回推荐的12个课程的id list
     */
    @Override
    public List<Integer> getTopClicksForNavSpecified(List<Integer> ids) throws CourseException {
        try {
            return courseDaoByDB.getTopClicksForNavSpecified(ids);
        } catch (CourseException e) {
            throw new CourseException("trying to get courses from DB failed! check DB connection.", CourseConstant.REC_COURSE_GET_ERROR);
        }
    }

    /**
     * 根据navId，去redis获取随机的3个课程id
     * 抛出2种错误，一种是还未生成过，一种是已经过期，都需要重新生成，报错很明白
     * @param navId
     * @return
     * @throws CourseException
     */
    @Override
    public List<Integer> getRandomRecCoursesId(Integer navId) throws CourseException {
        return courseDaoByRedis.getRandomRecCoursesId(navId);
    }

    /**
     * 3. 课程id放入redis
     * @param navId
     * @param coursesId
     * @return
     */
    @Override
    public Boolean setRecCoursesIdInRedis(Integer navId, List<Integer> coursesId) {
        return courseDaoByRedis.setRecCoursesIdInRedis(navId, coursesId);
    }
}
