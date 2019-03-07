package com.mark.manager.dao;

import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
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
    public Boolean checkCommentsIfExistInDB(Integer lessonId) {
        return null;
    }

    @Override
    public Boolean checkCommentIfExistInRedis(Integer commentId, Integer lessonId) {
        return null;
    }

    @Override
    public void setCommentsByLessonId(List<Comment> comments, Integer lessonId) {
    }

    @Override
    public void setSupportRateForComment(CommentRate commentRate) {
    }
}
