package com.mark.manager.service;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;

import java.util.List;
import java.util.Map;

public interface CourseService {
    Courses getCourse(Integer courseId);
    List<Courses> getCoursesByPid(List<Integer> ids);
    PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);
    Courses updateCourse(CourseUpdate courseUpdate) throws CourseException;
    Courses createCourse(Courses courses) throws CourseException;
    Map<Integer, List<Courses>> getIndexCoursesInfo(Map<Integer, List<Integer>> navIds) throws CourseException;
    boolean indexCoursesIsExisted(Integer navId);

    Long test();
}
