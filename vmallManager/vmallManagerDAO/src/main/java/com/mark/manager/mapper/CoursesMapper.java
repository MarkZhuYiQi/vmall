package com.mark.manager.mapper;

import com.mark.manager.dto.Courses;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CoursesMapper {
    public List<Courses> getCoursesByPid(@Param("pid") String pid);
    public List<Courses> getCourses(@Param("idRange")Map<String, String> idRange);
    public Long getCoursesCount();
    public String getCourseId(@Param("courseId") Long courseId, @Param("fragment") Integer fragment);
}
