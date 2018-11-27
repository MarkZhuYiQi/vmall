package com.mark.manager.serviceImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.LessonDao;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
@Repository
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonDao lessonDao;
    private static final String lessonPrefix = "lesson";
    @PostConstruct
    @Override
    public List<VproCoursesLessonList> getLessonsList() {
        System.out.println(lessonDao.getLessonsList());
        return null;
    }
}
