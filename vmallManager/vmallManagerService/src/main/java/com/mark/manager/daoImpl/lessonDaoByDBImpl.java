package com.mark.manager.daoImpl;

import com.mark.manager.dao.LessonDao;
import com.mark.manager.mapper.VproCoursesLessonListMapper;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.pojo.VproCoursesLessonListExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class lessonDaoByDBImpl implements LessonDao {
    @Autowired
    VproCoursesLessonListMapper vproCoursesLessonListMapper;

    @Override
    public List<VproCoursesLessonList> getLessonsList() {
        VproCoursesLessonListExample vproCoursesLessonListExample = new VproCoursesLessonListExample();
        vproCoursesLessonListExample.createCriteria();
        return vproCoursesLessonListMapper.selectByExample(vproCoursesLessonListExample);
    }

}
