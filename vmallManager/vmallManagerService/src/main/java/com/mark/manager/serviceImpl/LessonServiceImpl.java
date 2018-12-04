package com.mark.manager.serviceImpl;
import com.mark.manager.daoImpl.LessonDaoByDBImpl;
import com.mark.manager.daoImpl.LessonDaoByRedisImpl;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.IntrospectionException;
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

    @Override
    public VproCoursesLessonList getLesson(Integer lessonId) {
        VproCoursesLessonList vproCoursesLessonList = new VproCoursesLessonList();
        // 先去redis获取，没拿到就去数据库找，但是看结果是否命中，如果没有命中则直接返回空，
        // 同时可以给这个键放一个空值。
        // 为了防止缓存穿透，给这个访问ip做一个限制，如果这个访问多次没有命中，就直接封杀这个IP
        try {
            vproCoursesLessonList = lessonDaoByRedis.getLesson(lessonId);
            if (vproCoursesLessonList == null) {
                vproCoursesLessonList = lessonDaoByDB.getLesson(lessonId);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return vproCoursesLessonList;
    }

    public
}
