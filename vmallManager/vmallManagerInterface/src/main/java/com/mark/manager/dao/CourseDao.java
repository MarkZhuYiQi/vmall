package com.mark.manager.dao;

import com.github.pagehelper.PageInfo;
import com.mark.manager.dto.Courses;

import java.util.List;

public interface CourseDao {
    public Courses getCourse(String courseId);
    public List<Courses> getCoursesByPid(List<Integer> ids);
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);
}
