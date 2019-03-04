package com.mark.manager.dao;

import com.mark.common.exception.CommentException;
import com.mark.manager.pojo.VproComment;

import java.util.List;

public abstract class CommentDaoAbstract implements CommentDao {
    @Override
    public List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException {
        return null;
    }

}
