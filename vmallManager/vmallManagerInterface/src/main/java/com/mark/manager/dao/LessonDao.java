package com.mark.manager.dao;

import com.mark.common.exception.LessonException;
import com.mark.manager.pojo.VproCoursesLessonList;

import java.util.List;

public interface LessonDao{
    List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException;
    VproCoursesLessonList getLesson(Integer lessonId) throws LessonException;
}
