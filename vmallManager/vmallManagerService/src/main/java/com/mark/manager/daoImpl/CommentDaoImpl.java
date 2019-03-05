package com.mark.manager.daoImpl;

import com.mark.common.constant.CommentConstant;
import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dao.CommentDao;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.dto.Comment;
import com.mark.manager.pojo.VproComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("commentDao")
public class CommentDaoImpl extends CommentDaoAbstract {
    private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
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

    @Override
    public CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException {
        try {
            return commentDaoByRedis.getCommentsForShowByLessonId(lessonId, pageNum, pageSize);
        } catch (CommentException e) {
            CommentResult commentResult = new CommentResult();
            commentResult.setPageSize(pageSize);
            commentResult.setPageNum(pageNum);
            commentResult.setTotal(0L);
            commentResult.setCommentList(new ArrayList<>());
            if (e.getCode().equals(CommentConstant.COMMENT_LIST_NOT_EXIST_IN_REDIS)) {
                logger.info("{}, lessonId: {}", e.getMsg(), lessonId);
                Boolean cInDB = commentDaoByDB.checkCommentIfExistInDB(lessonId);
                if (!cInDB) throw new CommentException("comment need generate first! lessonId: " + String.valueOf(lessonId), e.getCode());
                return commentResult;
            }
            if (e.getCode().equals(CommentConstant.COMMENT_GAIN_OUT_OF_RANGE)) {
                return commentResult;
            }
            throw new CommentException(e.getMsg(), e.getCode());
        }
    }
}
