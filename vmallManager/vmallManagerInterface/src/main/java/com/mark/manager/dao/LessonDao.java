package com.mark.manager.dao;

import com.mark.manager.pojo.VproCoursesLessonList;

import java.beans.IntrospectionException;
import java.util.List;

public interface LessonDao{
    public List<VproCoursesLessonList> getLessonsList(Integer courseId);
    public VproCoursesLessonList getLesson(Integer lessonId) throws IllegalAccessException, IntrospectionException, InstantiationException;
}
