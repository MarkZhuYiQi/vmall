package com.mark.manager.mapper;

import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproCoursesLessonList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface CoursesMapper {
    List<Courses> getCoursesByPid(
            @Param("pid") String pid
    );
    List<Courses> getCourses(
            @Param("idRange")Map<String, String> idRange
    );
    Long getCoursesCount();
    String getCourseId(
            @Param("courseId") Long courseId,
            @Param("fragment") Integer fragment
    );
    List<Courses> getIndexCoursesInfo(
            @Param("navIds") List<Integer> navIds
    );
    List<Courses> getCoursesInfoForCatalog(
            @Param("navIds") List<Integer> navIds
    );
    Courses getCourseForDetail(
            @Param("courseId") Integer courseId
    );
}
