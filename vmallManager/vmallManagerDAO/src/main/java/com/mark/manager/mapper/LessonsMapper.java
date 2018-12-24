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
            // direction type + location type(up + before = 2, down + after = 3)
            @Param("opsType") Integer opsType,
            @Param("isTitle") Integer isTitle
    );
    Integer getLessonLidSpecified(
            @Param("type") Integer type,
            @Param("courseId") Integer courseId,
            @Param("lessonPid") Integer lessonPid
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
            @Param("lessonIsChapterHead") Integer lessonIsChapterHead,
            @Param("lessonPid") Integer lessonPid
    );
}
