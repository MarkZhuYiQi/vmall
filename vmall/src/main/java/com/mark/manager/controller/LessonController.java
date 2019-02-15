package com.mark.manager.controller;

import com.mark.common.exception.LessonException;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("lesson")
public class LessonController {
    @Reference()
    private LessonService lessonService;

    @GetMapping("list/{courseId:\\d+}")
    @ResponseBody
    public Result getLessonsList(@PathVariable Integer courseId) {
        try {
            System.out.println(lessonService);
            List<VproCoursesLessonList> lessons = lessonService.getLessonsList(courseId);
            return new Result(lessons);
        } catch (LessonException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @GetMapping("list")
    public Result test() {
        return new Result("666");
    }
}
