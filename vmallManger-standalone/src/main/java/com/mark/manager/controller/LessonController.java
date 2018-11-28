package com.mark.manager.controller;

import com.mark.manager.pojo.VproCoursesLessonList;
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
    public List<VproCoursesLessonList> getCourseLessonsList(@PathVariable Integer courseId) {
        return lessonService.getLessonsList(courseId);
    }
}
