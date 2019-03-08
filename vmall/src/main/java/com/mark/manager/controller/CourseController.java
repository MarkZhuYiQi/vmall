package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CartException;
import com.mark.common.exception.CategoryException;
import com.mark.common.exception.CourseException;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.CartService;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("course")
public class CourseController {
    @Reference()
    private CourseService courseService;
    @Reference()
    private CategoryService categoryService;
    @Reference()
    private CartService cartService;

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
    @GetMapping("detail/{courseId:\\d+}")
    @ResponseBody
    public Result getCoursesForDetail(@PathVariable Integer courseId) {
        try {
            Courses courses = courseService.getCourseForDetail(courseId);
            return new Result(courses);
        } catch (CourseException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }

    /**
     * 进入下单确认页面时先检查购物车中的课程是否还在售
     * 提交的课程是否就是购物车中的那些元素
     * @param list
     * @return
     */
    @PostMapping("check")
    @ResponseBody
    public Result checkCourses(@RequestBody List<String> list) {
        if (list.size() <= 0) return new Result("No Course send for availability check!", CourseConstant.NO_COURSE_ID_SEND_FOR_AVAILABILITY_CHECK);
        JwtUserDetails detail = (JwtUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = detail.getUserId();
        System.out.println(detail);
        System.out.println(userId);
        try {
            cartService.verifyCartItem(list, userId);
            List<String> res = courseService.checkCourses(list);
            return new Result(res);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @GetMapping("rec/{courseId:\\d+}")
    @ResponseBody
    public Result getRecCourses(@PathVariable("courseId") Integer courseId) {
        try {
            Courses courses = courseService.getCourseForDetail(courseId);
            List<Courses> list = courseService.getRecCoursesByNavIds(courses.getCoursePid());
            return new Result(list);
        } catch (CourseException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
}
