package com.mark.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LessonsMapper {
    int updateLessonsSequence(
            @Param("lessonIds") List<Integer> lessonIds,
            @Param("type") Integer type,
            @Param("offset") Integer offset,
            @Param("courseId") Integer courseId,
            @Param("lessonPid") Integer lessonPid,
            @Param("isTitle") Integer isTitle
        );
    List<Integer> getLessonsNeedReLocation(
            @Param("start") Integer start,
            @Param("end") Integer end,
            @Param("courseId") Integer courseId,
            @Param("type") Integer type,
            @Param("isTitle") Integer isTitle
    );
    Integer getLessonLidSpecified(
            @Param("type") Integer type,
            @Param("courseId") Integer courseId,
            @Param("courseLid") Integer courseLid
    );
    Integer getSubTitleLessonIdByLessonLid(
            @Param("lessonLid") Integer lessonLid
    );
    List<Integer> getLessonsInnerTitle(
            @Param("lessonIds") List<Integer> lessonIds,
            @Param("courseId") Integer courseId
    );
    Integer getLastLessonLid(
            @Param("courseId") Integer courseId,
            @Param("lessonPid") Integer lessonPid
    );
}
