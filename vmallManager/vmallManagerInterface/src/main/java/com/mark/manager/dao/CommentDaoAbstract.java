package com.mark.manager.dao;

import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.pojo.VproComment;

import java.util.List;

public abstract class CommentDaoAbstract implements CommentDao {
    @Override
    public List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException {
        return null;
    }

    @Override
    public CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException {
        return null;
    }

    @Override
    public Boolean checkCommentIfExistInDB(Integer lessonId) {
        return null;
    }
}
