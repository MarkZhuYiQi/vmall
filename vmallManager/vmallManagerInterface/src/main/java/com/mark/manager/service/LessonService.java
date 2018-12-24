package com.mark.manager.service;

import com.mark.manager.dto.LessonsOps;
import com.mark.manager.dto.LessonsOpsList;
import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;
import java.util.List;

public interface LessonService {
    List<VproCoursesLessonList> getLessonsList(Integer courseId);
    VproCoursesLessonList getLesson(Integer lessonId);
    List<Integer> getLessonsNeedReLocation(LessonsOps lessonsOps);
    VproCoursesLessonList insertLessonToLocationSpecified();
    VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination);
    boolean adjustLessonSequence(List<Integer> lessonIds, Integer type, Integer offset, Integer courseId, Integer lessonPid, Integer isTitle);
    boolean removeLesson(LessonsOps lessonsOps);
    VproCoursesLessonList addLesson(LessonsOps lessonsOps);
    boolean moveLesson(LessonsOps lessonsOps);
    VproCoursesLessonList addSubTitle(LessonsOps lessonsOps);
    boolean moveSubTitle(LessonsOps lessonsOps);
    boolean removeSubTitle(LessonsOps lessonsOps);
    boolean manageEdit(LessonsOpsList lessonsOpsList);
}
