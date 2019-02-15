package com.mark.manager.daoImpl;

import com.mark.common.constant.LessonsConstant;
import com.mark.common.exception.LessonException;
import com.mark.common.util.LogUtil;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.pojo.VproCoursesLessonList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lessonDao")
public class LessonDaoImpl implements LessonDao {
    private final static Logger logger = LoggerFactory.getLogger(LessonDaoImpl.class);
    @Autowired
    @Qualifier("lessonDaoByDB")
    LessonDao lessonDaoByDB;

    @Autowired
    @Qualifier("lessonDaoByRedis")
    LessonDao lessonDaoByRedis;

    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException {
        try {
            return lessonDaoByRedis.getLessonsList(courseId);
        } catch (LessonException e) {
            logger.warn("get lessons list from cache failed. {}->{}: {}", LogUtil.getObjectName(), LogUtil.funcName(), e.getMsg());
            try {
                return lessonDaoByDB.getLessonsList(courseId);
            } catch (LessonException e1) {
                String err = "get lessonsList failed! " + e1.getMessage();
                logger.error(err);
                throw new LessonException(err, LessonsConstant.LESSONS_LIST_GET_FAILED);
            }
        }
    }

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) throws LessonException {
        try {
            return lessonDaoByRedis.getLesson(lessonId);
        } catch (LessonException e) {
            logger.warn(e.getMsg() + "{}->{}: {}", LogUtil.getObjectName(), LogUtil.funcName());
            try {
                return lessonDaoByDB.getLesson(lessonId);
            } catch (LessonException e1) {
                String err = "get lesson failed!" + e1.getMessage();
                logger.error(err);
                throw new LessonException(err, LessonsConstant.LESSON_GET_FAILED);
            }
        }
    }
}
