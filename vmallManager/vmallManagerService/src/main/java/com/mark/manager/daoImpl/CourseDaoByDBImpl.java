package com.mark.manager.daoImpl;

import com.github.pagehelper.PageInfo;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.dto.Courses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDaoByDBImpl implements CourseDao {
    @Override
    public Courses getCourse(String courseId) {
        return null;
    }

    @Override
    public List<Courses> getCoursesByPid(List<Integer> ids) {
        return null;
    }

    @Override
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids) {
        return null;
    }
}
