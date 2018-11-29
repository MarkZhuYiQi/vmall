package com.mark.manager.controller;

import com.github.pagehelper.PageInfo;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("{navId:\\d+}")
    public Result getCoursesByPid(
            @PathVariable("navId") Integer navId,
            @RequestParam(name = "p", defaultValue = "1") Integer currentPage,
            @RequestParam(name = "s", defaultValue = "10") Integer pageSize
    )
    {
        List<Integer> idList = categoryService.getSubIdFromCategory(navId, categoryService.getCategories(), new ArrayList<Integer>());
//        List<Courses> courses = courseService.getCoursesByPid(idList);

        PageInfo<Courses> courses = courseService.getCoursesByPid(currentPage, pageSize, idList);

        return new Result(courses);
    }
}