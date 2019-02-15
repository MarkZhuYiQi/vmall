package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.LessonException;
import com.mark.manager.bo.Result;
import com.mark.manager.pojo.VproCoursesLessonList;
import com.mark.manager.service.LessonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("lesson")
public class LessonController {
    private static final Logger logger = LoggerFactory.getLogger(LessonController.class);
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
    @ResponseBody
    public Result test() {
        return new Result("666");
    }
}
