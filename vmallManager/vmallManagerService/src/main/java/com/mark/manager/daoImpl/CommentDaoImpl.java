package com.mark.manager.daoImpl;

import com.mark.common.constant.CommentConstant;
import com.mark.common.exception.CommentException;
import com.mark.manager.dao.CommentDao;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("commentDao")
public class CommentDaoImpl extends CommentDaoAbstract {
    @Autowired
    @Qualifier("commentDB")
    CommentDao commentDaoByDB;

    @Autowired
    @Qualifier("commentRedis")
    CommentDao commentDaoByRedis;
    @Override
    public List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException {
        try {
            return commentDaoByDB.getCommentsByLessonId(lessonId);
        } catch (CommentException e) {
            throw new CommentException(e.getMsg(), CommentConstant.COMMENTS_GET_FROM_DB_FAILED);
        }
    }

    @Override
    public VproComment setComment(VproComment vproComment) throws CommentException {
        try {
            VproComment v = commentDaoByDB.setComment(vproComment);
            commentDaoByRedis.setComment(v);
            return v;
        } catch (CommentException e) {
            throw new CommentException(e.getMsg(), CommentConstant.COMMENT_SET_FAILED);
        }
    }
}
