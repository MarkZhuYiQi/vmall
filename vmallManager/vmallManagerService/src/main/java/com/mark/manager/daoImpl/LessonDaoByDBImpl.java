package com.mark.manager.daoImpl;

import com.mark.manager.dao.LessonDao;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.pojo.VproCoursesLessonListExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LessonDaoByDBImpl implements LessonDao {
    @Autowired
    VproCoursesLessonListMapper vproCoursesLessonListMapper;

    @Override
    public List<VproCoursesLessonList> getLessonsList(Integer courseId) {
        VproCoursesLessonListExample vproCoursesLessonListExample = new VproCoursesLessonListExample();
        vproCoursesLessonListExample.createCriteria().andLessonCourseIdEqualTo(courseId);
        System.out.println("db: " + courseId);
        return vproCoursesLessonListMapper.selectByExample(vproCoursesLessonListExample);
    }
}
