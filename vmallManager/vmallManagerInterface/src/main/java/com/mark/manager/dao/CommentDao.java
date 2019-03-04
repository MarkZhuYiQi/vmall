package com.mark.manager.dao;

import com.mark.common.exception.CommentException;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;

import java.util.List;

public interface CommentDao {
    List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException;
}
