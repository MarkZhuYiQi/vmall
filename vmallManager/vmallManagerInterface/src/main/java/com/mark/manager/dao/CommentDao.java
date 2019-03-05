package com.mark.manager.dao;

import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;

import java.util.List;

public interface CommentDao {
    List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException;
    VproComment setComment(VproComment vproComment) throws CommentException;
    CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException;
    Boolean checkCommentIfExistInDB(Integer lessonId);
}
