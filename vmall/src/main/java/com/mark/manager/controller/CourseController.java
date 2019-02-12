package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CategoryException;
import com.mark.manager.bo.Result;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("course")
public class CourseController {
    @Reference()
    CourseService courseService;
    @Reference()
    CategoryService categoryService;

    @GetMapping("catalogs")
    public Result getCoursesForCatalogs(@RequestParam("p") Integer page, @RequestParam("c") Integer navId) {
        try {
            categoryService.getSubIds(navId);
        } catch (CategoryException e) {
            e.printStackTrace();
        }
    }
}
