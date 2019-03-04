package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.CommentException;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;
import com.mark.manager.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "comment")
public class CommentController {
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
}
