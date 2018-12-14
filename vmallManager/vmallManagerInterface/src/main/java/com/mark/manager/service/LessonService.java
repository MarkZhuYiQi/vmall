package com.mark.manager.service;

import com.mark.manager.dto.LessonsOps;
import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;
import java.util.List;

public interface LessonService {
    List<VproCoursesLessonList> getLessonsList(Integer courseId);
    VproCoursesLessonList getLesson(Integer lessonId);
    VproCoursesLessonList insertLessonToLocationSpecified();
    VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination);
    boolean adjestLessonSequence(List<Integer> lessonIds, Byte type, Integer offset, Integer courseId, Integer lessonPid);
    List<VproCoursesLessonList> checkIfHasLessonsUnderSubTitle();
    VproCoursesLessonList insertSubTitle();
    VproCoursesLessonList updateSubTitle();
    boolean moveLessonInnerTitle(LessonsOps lessonsOps);
    boolean moveLessonOuterTitle(LessonsOps lessonsOps);
    boolean removeLesson();

}
