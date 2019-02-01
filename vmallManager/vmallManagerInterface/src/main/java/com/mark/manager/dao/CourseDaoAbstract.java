package com.mark.manager.dao;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.CourseException;
import com.mark.manager.dto.Courses;

import java.util.List;
import java.util.Map;

public abstract class CourseDaoAbstract implements CourseDao {
    @Override
    public abstract Courses getCourse(String courseId);

    @Override
    public abstract List<Courses> getCoursesByPid(List<Integer> ids);

    @Override
    public abstract PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids);

    @Override
    public List<Courses> getIndexCoursesInfo(Integer navPid, List<Integer> navIds) throws CourseException {
        return null;
    }

    @Override
    public boolean indexCoursesIsExisted(Integer navId) {
        return false;
    }

    @Override
    public Map<Integer, List<Courses>> getIndexCoursesCache(Integer indexNavId) throws CourseException {
        return null;
    }

    @Override
    public void setIndexCoursesCache(Integer indexNavId, Map<Integer, List<Courses>> indexCoursesCache) {
    }


}
