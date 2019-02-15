package com.mark.manager.service;

import com.mark.common.exception.LessonException;
import com.mark.manager.dto.LessonsOps;
import com.mark.manager.dto.LessonsOpsList;
import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;
import java.util.List;

public interface LessonService {
    List<VproCoursesLessonList> getLessonsList(Integer courseId) throws LessonException;
    VproCoursesLessonList getLesson(Integer lessonId) throws LessonException;
    List<Integer> getLessonsNeedReLocation(LessonsOps lessonsOps) throws LessonException;
    VproCoursesLessonList insertLessonToLocationSpecified();
    VproCoursesLessonList updateLessonToLocationSpecified(VproCoursesLessonList original, VproCoursesLessonList destination) throws LessonException;
    boolean adjustLessonSequence(List<Integer> lessonIds, Integer type, Integer offset, Integer courseId, Integer lessonPid, Integer isTitle);
    boolean removeLesson(LessonsOps lessonsOps) throws LessonException;
    VproCoursesLessonList addLesson(LessonsOps lessonsOps) throws LessonException;
    boolean moveLesson(LessonsOps lessonsOps) throws LessonException;
    VproCoursesLessonList addSubTitle(LessonsOps lessonsOps) throws LessonException;
    boolean moveSubTitle(LessonsOps lessonsOps) throws LessonException;
    boolean removeSubTitle(LessonsOps lessonsOps) throws LessonException;
    boolean manageEdit(LessonsOpsList lessonsOpsList) throws LessonException;
}
