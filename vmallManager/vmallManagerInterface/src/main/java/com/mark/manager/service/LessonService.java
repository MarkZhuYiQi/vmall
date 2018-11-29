package com.mark.manager.service;

import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;
import java.util.List;

public interface LessonService {
    public List<VproCoursesLessonList> getLessonsList(Integer courseId);
    public VproCoursesLessonList getLesson(Integer lessonId);

}
