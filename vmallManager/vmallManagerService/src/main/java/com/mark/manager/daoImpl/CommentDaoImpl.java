package com.mark.manager.daoImpl;

import com.mark.common.constant.CommentConstant;
import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dao.CommentDao;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
import com.mark.manager.pojo.VproComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 首先去redis直接获取评论，有则返回；
     * 没有就判断报错类型：
     *      判断数据库有评论，那么抛未生成错误
     *      判断没有评论，直接返回空对象
     * @param lessonId
     * @param pageNum
     * @param pageSize
     * @return
     * @throws CommentException
     */
    @Override
    public CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException {
        try {
            logger.info("CommentDaoImpl.getCommentsForShowByLessonId");
            return commentDaoByRedis.getCommentsForShowByLessonId(lessonId, pageNum, pageSize);
        } catch (CommentException e) {
            CommentResult commentResult = new CommentResult();
            commentResult.setPageSize(pageSize);
            commentResult.setPageNum(pageNum);
            commentResult.setTotal(0L);
            commentResult.setCommentList(new ArrayList<>());
            if (e.getCode().equals(CommentConstant.COMMENT_LIST_NOT_EXIST_IN_REDIS)) {
                logger.info("{}, lessonId: {}", e.getMsg(), lessonId);
                Boolean cInDB = commentDaoByDB.checkCommentsIfExistInDB(lessonId);
                logger.info("check comment if exist in DB ? {}", cInDB);
                if (cInDB) {
                    logger.info("comment need generate first! lessonId: " + String.valueOf(lessonId));
                    throw new CommentException("comment need generate first! lessonId: " + String.valueOf(lessonId), e.getCode());
                }
                return commentResult;
            }
            if (e.getCode().equals(CommentConstant.COMMENT_GAIN_OUT_OF_RANGE)) {
                return commentResult;
            }
            throw new CommentException(e.getMsg(), e.getCode());
        }
    }

    @Override
    public void setCommentsByLessonId(List<Comment> comments, Integer lessonId) {
        commentDaoByRedis.setCommentsByLessonId(comments, lessonId);
    }

    @Override
    public Boolean checkCommentIfExistInRedis(Integer commentId, Integer lessonId) {
        return commentDaoByRedis.checkCommentIfExistInRedis(commentId, lessonId);
    }

    @Override
    public void setSupportRateForComment(CommentRate commentRate) {
        commentDaoByRedis.setSupportRateForComment(commentRate);
    }
    public Map<Integer, Map<String, Integer>> getCommentSupportRate(List<String> commentsId) {
        return commentDaoByRedis.getCommentSupportRate(commentsId);
    }
}
