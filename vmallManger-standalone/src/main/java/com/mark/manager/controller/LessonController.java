package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.dto.LessonsOps;
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
    public Result getCourseLessonsList(@PathVariable Integer courseId) {
        return new Result(lessonService.getLessonsList(courseId));
    }

    @PostMapping("")
    public Result reLocateLesson(@RequestBody LessonsOps lessonsOps) {
        switch(lessonsOps.getOps()) {
            case 101:
                lessonService.addLesson(lessonsOps);
                break;
            case 102:
                boolean res = lessonService.moveLesson(lessonsOps);
                break;
            case 103:
                boolean res = lessonService.removeLesson(lessonsOps);
                break;
            case 201:
                boolean res = lessonService.addSubTitle(lessonsOps);
                break;
            case 202:
                lessonService.moveSubTitle(lessonsOps);
                break;
            case 203:
                lessonService.removeLesson(lessonsOps);

        }
        return new Result();
    }
}