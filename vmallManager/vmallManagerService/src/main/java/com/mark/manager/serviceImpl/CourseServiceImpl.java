package com.mark.manager.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.mapper.VproCoursesMapper;
import com.mark.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    VproCoursesMapper vproCoursesMapper;
    @Autowired
    CoursesMapper coursesMapper;
    @Override
    public List<Courses> getCoursesByPid(List<Integer> ids)
    {
        String pids = getCoursePidCriteria(ids);
        List<Courses> courses = coursesMapper.getCoursesByPid(pids);
        return courses;
    }
    private String getCoursePidCriteria(List<Integer> ids)
    {
        StringBuilder pids = new StringBuilder();
        for(int i = ids.size() - 1; i >= 0; i--)
        {
            pids.append(ids.get(i).toString());
            if (i != 0) pids.append(",");
        }
        return pids.toString();
    }
    @Override
    public PageInfo<Courses> getCoursesByPid(int currentPage, int pageSize, List<Integer> ids)
    {
        PageHelper.startPage(currentPage, pageSize);
        List<Courses> courses = coursesMapper.getCoursesByPid(getCoursePidCriteria(ids));
        PageInfo page = new PageInfo(courses);
        return page;
    }
}
