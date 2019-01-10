package com.mark.manager.controller;

import com.github.pagehelper.PageInfo;
import com.mark.common.constant.CourseConstant;
import com.mark.common.exception.CourseException;
import com.mark.common.validateGroup.CreateCourse;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.AuthService;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.mark.manager.validator.ValidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mark.common.constant.CourseConstant.CREATE_COURSE_VALIDATE_ERROR;
import static com.mark.common.constant.ResultConstant.RES_NULL;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    AuthService authService;
    @Autowired
    Validator localValidator;
    @GetMapping("nav/{navId:\\d+}")
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
    @GetMapping("{courseId:\\d+}")
    public Result getCourseById(@PathVariable("courseId") Integer courseId) {
        Courses courses = courseService.getCourse(courseId);
        return new Result(courses);
    }
    @PostMapping("")
    public Result updateCourse(@RequestBody CourseUpdate courseUpdate) {
        Courses courses = courseService.updateCourse(courseUpdate);
        if (courses != null) return new Result(courses);
        return new Result(null, RES_NULL);
    }
    @PutMapping("")
    public Result createCourse(@Validated({CreateCourse.class}) @RequestBody Courses courses) {
        // 从security中获取用户信息，实际上可以存对象，默认Object
        String appAuthId = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 从redis获得用户信息
        Map<String, String> list = authService.getAuthByAuthIdFromRedis(appAuthId);
        if (list.size() == 0) return new Result("author could not be found", CourseConstant.INSERT_COURSE_AUTHOR_FAILURE);
        // 后端前端比对，如果不一样说明作者有问题
        if (!list.get("authAppid").equals(courses.getCourseAuthor())) return new Result("author mismatch", CourseConstant.INSERT_COURSE_AUTHOR_FAILURE);
        courses.setCourseAuthor(list.get("authId"));
        try {
            Courses course = courseService.createCourse(courses);
            System.out.println(course.toString());
            return new Result(course);
        } catch (CourseException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }
}
