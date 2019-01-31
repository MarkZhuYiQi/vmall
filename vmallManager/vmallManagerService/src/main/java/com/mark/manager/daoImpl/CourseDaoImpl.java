package com.mark.manager.daoImpl;

import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.util.LogUtil;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.Courses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("courseDao")
public class CourseDaoImpl implements CourseDao {
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
            indexCourses = courseDaoByRedis.getIndexCoursesInfo(navPid, navIds);
        } catch (CourseException e) {
            err = String.format("%s->%s: %s, navPid: %d, navIds: %s", LogUtil.getObjectName(), LogUtil.funcName(), e.getMsg(), navPid, navIds.toString());
            logger.info(err);
            try {
                indexCourses = courseDaoByDB.getIndexCoursesInfo(navPid, navIds);
            } catch (CourseException ex) {
                err = String.format("%s->%s: %s, navPid: %d, navIds: %s", LogUtil.getObjectName(), LogUtil.funcName(), e.getMsg(), navPid, navIds.toString());
                logger.info(err);
                throw new CourseException("Get index courses failed! check the params: " + navIds, CourseConstant.GET_INDEX_COURSES_INFO_FAILED);
            }
        }
        return indexCourses;
    }

    @Override
    public boolean indexCoursesIsExisted(Integer navId) {

    }
}
