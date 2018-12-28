package com.mark.manager.service;

import com.github.pagehelper.PageInfo;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;

import java.util.List;

public interface CourseService {
    public Courses getCourse(Integer courseId);
    public List<Courses> getCoursesByPid(List<Integer> ids);
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);
    public Courses updateCourse(CourseUpdate courseUpdate);

}
