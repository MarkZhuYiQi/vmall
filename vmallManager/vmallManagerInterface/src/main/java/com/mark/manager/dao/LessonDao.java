package com.mark.manager.dao;

import com.mark.manager.pojo.VproCoursesLessonList;

import java.util.List;

public interface LessonDao{
    public List<VproCoursesLessonList> getLessonsList(Integer courseId);
}
