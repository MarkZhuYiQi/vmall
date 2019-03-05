package com.mark.manager.service;

import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;

import java.util.List;

public interface CommentService {
    List<Comment> genCommentList(Integer lessonId) throws CommentException;
    List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException;
    List<Comment> genCommentRelations(List<VproComment> vproComments);
    CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException;
}
