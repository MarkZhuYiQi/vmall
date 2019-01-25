package com.mark.manager.dao;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.manager.dto.Courses;

import java.util.List;
import java.util.Map;

public interface CourseDao {
    Courses getCourse(String courseId);
    List<Courses> getCoursesByPid(List<Integer> ids);
    PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);
    List<Courses> getIndexCoursesInfo(List<Integer> navIds) throws CourseException;
}
