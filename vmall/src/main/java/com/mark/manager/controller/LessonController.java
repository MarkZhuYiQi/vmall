package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("lesson")
public class LessonController {
    @Reference()
    LessonService lessonService;

    @GetMapping("list/{courseId:\\d+}}")
    public Result getLessonsList(@PathVariable Integer courseId) {
        List<VproCoursesLessonList> lessons = lessonService.getLessonsList(courseId);
        return new Result(lessons);
    }
}
