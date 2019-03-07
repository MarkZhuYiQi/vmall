package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
import com.mark.manager.pojo.VproComment;
import com.mark.manager.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger("CommentController");

    @Reference()
    private CommentService commentService;

    @GetMapping("test")
    public Result test() {
        try {
            List<VproComment> list = commentService.getCommentsByLessonId(27675);
            List<Comment> comments = commentService.genCommentRelations(list);
            return new Result(comments);
        } catch (CommentException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @GetMapping("{lessonId}")
    public Result getCommentList(
                @PathVariable("lessonId") Integer lessonId,
                @RequestParam(name = "p", defaultValue = "1") Integer pageNum,
                @RequestParam(name = "s", defaultValue = "10") Integer pageSize
            ) {
        try {
            CommentResult commentResult = commentService.getCommentsForShowByLessonId(lessonId, pageNum, pageSize);
            System.out.println(commentResult.toString());
            return new Result(commentResult);
        } catch (CommentException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @PostMapping("support")
    public Result commentSupportRate(@RequestBody @Validated CommentRate commentRate) {
        commentService.commentSupportRate(commentRate);
        return new Result(true);
    }
}
