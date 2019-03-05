package com.mark.manager.service;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CartException;
import com.mark.common.exception.CategoryException;
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
    Map<Integer, List<Courses>> getIndexCoursesInfo(Integer indexNavId, Map<Integer, List<Integer>> navIds) throws CourseException;
    Map<Integer, List<Courses>> getIndexCoursesInfoCache(Integer indexNavId) throws CourseException;
    boolean indexCoursesIsExisted(Integer navId);
    PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException;
    Courses getCourseForDetail(Integer courseId) throws CourseException;
    List<String> checkCourses(List<String> coursesId) throws CartException;
    List<Courses> getRecCoursesByNavIds(Integer navId);
    void genRecCoursesByNavId(Integer navId) throws CategoryException, CourseException;


    Long test();
}
