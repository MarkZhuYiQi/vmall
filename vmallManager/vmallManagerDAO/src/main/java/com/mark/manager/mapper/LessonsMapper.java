package com.mark.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LessonsMapper {
    int updateLessonsSequence(
            @Param("lessonIds") List<Integer> lessonIds,
            @Param("type") Byte type,
            @Param("offset") Integer offset,
            @Param("courseId") Integer courseId,
            @Param("lessonId") Integer lessonId
        );
    List<Integer> getLessonsNeedReLocation(@Param("start") Integer start, @Param("end") Integer end, @Param("courseId") Integer courseId);

}
