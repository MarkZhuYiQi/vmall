package com.mark.manager.service;

import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
import com.mark.manager.pojo.VproComment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<Comment> genCommentList(Integer lessonId) throws CommentException;
    List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException;
    List<Comment> genCommentRelations(List<VproComment> vproComments);
    CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException;
    Boolean commentSupportRate(CommentRate commentRate);
    VproComment setComment(VproComment vproComment) throws CommentException;
    Map<Integer, Map<String, Integer>> getCommentSupportRate(List<String> commentsId);
}
