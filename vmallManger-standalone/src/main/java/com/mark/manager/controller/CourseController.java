package com.mark.manager.controller;

import com.github.pagehelper.PageInfo;
import com.mark.common.validateGroup.CreateCourse;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.CourseUpdate;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.mark.manager.validator.ValidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.mark.common.constant.CourseConstant.COURSE_VALIDATE_ERROR;
import static com.mark.common.constant.ResultConstant.RES_NULL;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CategoryService categoryService;

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
    public Result createCourse(
//            @Validated({CreateCourse.class})
            @RequestBody Courses courses) {
        System.out.println(courses.toString());
        ValidateDTO<Courses> validateDTO = new ValidateDTO<Courses>(courses);
//        String errMessage = validateDTO.proceedValidate();
        String errMessage = validateDTO.validatePojo();
        if (errMessage != null) {
            return new Result(errMessage, COURSE_VALIDATE_ERROR);
        }

        return new Result();
    }

}
