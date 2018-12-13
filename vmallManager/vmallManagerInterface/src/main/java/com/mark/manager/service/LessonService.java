package com.mark.manager.service;

import com.mark.manager.dto.LessonsOps;
import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;
import java.util.List;

public interface LessonService {
    List<VproCoursesLessonList> getLessonsList(Integer courseId);
    VproCoursesLessonList getLesson(Integer lessonId);
    VproCoursesLessonList insertLessonToLocationSpecified();
    VproCoursesLessonList updateLessonToLocationSpecified(LessonsOps lessonsOps);
    List<VproCoursesLessonList> checkIfHasLessonsUnderSubTitle();
    VproCoursesLessonList insertSubTitle();
    VproCoursesLessonList updateSubTitle();
    boolean removeLesson();

}
