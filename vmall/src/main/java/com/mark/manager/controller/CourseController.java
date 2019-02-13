package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    @Reference()
    private CourseService courseService;
    @Reference()
    private CategoryService categoryService;

    @GetMapping("catalogs")
    @ResponseBody
    public Result getCoursesForCatalogs(@RequestParam("p") Integer page, @RequestParam("c") Integer navId) {
        try {
            List<Integer> idList = categoryService.getSubIdFromCategory(navId);
            PageInfo<Courses> courses = courseService.getCoursesForCatalog(navId, page, CourseConstant.COURSES_FOR_CATALOG_SIZE, idList);
            return new Result(courses);
        } catch (CategoryException e) {
            return new Result(e.getCode(), e.getMessage());
        } catch (CourseException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }
}
