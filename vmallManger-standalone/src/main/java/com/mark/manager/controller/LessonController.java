package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lessons")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @GetMapping("{courseId:\\d+}")
    public Result getCourseLessonsList(@PathVariable Integer courseId) {
        return new Result(lessonService.getLessonsList(courseId));
    }
}