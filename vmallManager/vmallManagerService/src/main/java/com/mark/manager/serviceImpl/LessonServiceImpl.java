package com.mark.manager.serviceImpl;
import com.mark.manager.daoImpl.LessonDaoByDBImpl;
import com.mark.manager.daoImpl.LessonDaoByRedisImpl;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDaoByDBImpl lessonDaoByDB;
    @Autowired
    LessonDaoByRedisImpl lessonDaoByRedis;

    private static final String lessonPrefix = "lesson";
    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) {
        List<VproCoursesLessonList> list = lessonDaoByRedis.getLessonsList(courseId);
        if (lessonDaoByRedis.getLessonsList(courseId).size() == 0) {
            list = lessonDaoByDB.getLessonsList(courseId);
        }
        return list;
        /*List<VproCoursesLessonList> list = lessonDaoByRedis.getLessonsList();
        if (list.size() == 0) {
            try {
                lessonDaoByRedis.cacheLessonsList(lessonDaoByDB.getLessonsList());
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(list);
        }
        return list;*/
    }
}
