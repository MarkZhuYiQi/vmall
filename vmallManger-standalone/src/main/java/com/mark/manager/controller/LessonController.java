package com.mark.manager.controller;

import com.mark.common.constant.LessonsConstant;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.LessonsOps;
import com.mark.manager.dto.LessonsOpsList;
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
    public Result editLessonsList(@RequestBody LessonsOpsList lessonsOpsList) {
        if (lessonsOpsList.getLessonsOpsList().size() > 0) {
            Boolean res = lessonService.manageEdit(lessonsOpsList);
            return new Result(res);
        }
        return new Result("对lessons的修改失败", LessonsConstant.LESSONS_EDIT_FAILED);
    }
}



/*
{
    "courseId": "1414550568",
    "type": "1",
    "ops": "102",
    "isTitle": "0",
    "original": {
        "lessonId": "95246",
        "lessonLid": "2",
        "lessonPid": "95241",
        "lessonTitle": "测试课时2-2",
        "lessonIsChapterHead": "0",
        "lessonCourseId": "1414550568",
        "lessonIsDeleted": "0"
    },
    "destination": {
        "lessonId": "95240",
        "lessonLid": "0",
        "lessonPid": "95240",
        "lessonTitle": "测试课时1-1",
        "lessonIsChapterHead": "0",
        "lessonCourseId": "1414550568",
        "lessonIsDeleted": "0"
    }
}*/
/*
{
	"courseId": "1414550568",
	"type": "1",
	"ops": "202",
	"isTitle": "1",
	"original": {
		"lessonId": "95248",
		"lessonLid": "2",
		"lessonPid": "71",
		"lessonTitle": "测试章节3",
		"lessonIsChapterHead": "1",
		"lessonCourseId": "1414550568",
		"lessonIsDeleted": "0"
	},
	"destination": {
		"lessonId": "95241",
		"lessonLid": "1",
		"lessonPid": "71",
		"lessonTitle": "测试章节2",
		"lessonIsChapterHead": "1",
		"lessonCourseId": "1414550568",
		"lessonIsDeleted": "0"
	}
}
*/
