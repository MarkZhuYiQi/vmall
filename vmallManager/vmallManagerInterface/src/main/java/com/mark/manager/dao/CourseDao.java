package com.mark.manager.dao;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.manager.dto.Courses;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CourseDao {
    Courses getCourse(String courseId);
    List<Courses> getCoursesByPid(List<Integer> ids);
    PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);
    List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException;
    boolean indexCoursesIsExisted(Integer navId);
    Map<Integer, List<Courses>> getIndexCoursesCache(Integer indexNavId) throws CourseException;
    void setIndexCoursesCache(Integer indexNavId, Map<Integer, List<Courses>> indexCoursesCache);
    Set<String> checkIndexCourseCache() throws CourseException;
    PageInfo<Courses> getCoursesForCatalog(Integer navId, int currentPage, int pageSize, List<Integer> ids) throws CourseException;
}
