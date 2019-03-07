package com.mark.manager.serviceImpl;

import com.mark.common.constant.CommentConstant;
import com.mark.common.exception.CommentException;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dao.CommentDao;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.pojo.VproComment;
import com.mark.manager.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    @Qualifier("commentDao")
    CommentDao commentDao;

    private List<VproComment> data;

    /**
     * 初始化课程的评论列表
     * @param lessonId
     * @return
     * @throws CommentException
     */
    public List<Comment> genCommentList(Integer lessonId) throws CommentException {
        try {
            // 获得每一条评论
            List<VproComment> list = getCommentsByLessonId(lessonId);
            // 迭代出各个评论的关系
            List<Comment> comments = genCommentRelations(list);
            commentDao.setCommentsByLessonId(comments, lessonId);
            return comments;
        } catch (CommentException e) {
            throw new CommentException(e.getMsg(), e.getCode());
        }
    }

    /**
     * 去取得评论，但是评论不一定每篇都有，如果频繁刷新，数据库会崩溃，所以需要放入一个临时值
     * 拿到评论需要迭代每一条评论的关系，迭代他们的关系
     * redis存放评论的格式应该是[hash] VproComment_[lesson_id], [hkey]comment_id: [hvalue]comment_info
     * redis存放评论到list表VproComment_(lesson_id)_list
     * @return string
     */
    @Override
    public List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException {
        try {
            List<VproComment> vproComments = commentDao.getCommentsByLessonId(lessonId);
            data = new ArrayList<VproComment>(vproComments);
            return vproComments;
        } catch (CommentException e) {
            throw new CommentException(e.getMsg(), e.getCode());
        }
    }

    /**
     * [Comment1, Comment2, Comment3, ...]
     * @param vproComments
     * @return
     */
    @Override
    public List<Comment> genCommentRelations(List<VproComment> vproComments) {
        List<Comment> comments = new ArrayList<>();
        for (VproComment vproComment : vproComments) {
            Comment comment = new Comment();
            comment = DtoUtil.vproComment2Comment(vproComment);
            if (vproComment.getVproCommentReplyId() > 0) {
                comment = iterCommentParentRelation(comment);
            }
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException {
        logger.info("CommentServiceImpl.getCommentsForShowByLessonId");
        try {
            return commentDao.getCommentsForShowByLessonId(lessonId, pageNum, pageSize);
        } catch (CommentException e) {
            logger.warn(e.getMsg(), e.getCode());
            if (e.getCode().equals(CommentConstant.COMMENT_LIST_NOT_EXIST_IN_REDIS)) {
                List<Comment> comments = genCommentList(lessonId);
                return commentDao.getCommentsForShowByLessonId(lessonId, pageNum, pageSize);
            }
            throw new CommentException(e.getMsg(), e.getCode());
        }
    }

    /**
     * 迭代评论
     * 传入一条要找父系被回复的评论
     * 找到被回复的评论就塞入到传入评论的parents中
     * 如果被回复的评论上面还有，那么继续迭代
     * @param comment
     * @return Comment {xxxx: xxxx, parents: [{}, {}, {}]}
     */
    private Comment iterCommentParentRelation(Comment comment) {
        VproComment o = null;
        for (VproComment vproComment : data) {
            // 拿到父级层级
            List<VproComment> parents = comment.getParents();
            // 拿到上一轮的元素，寻找他的父亲
            VproComment prev;
            if (parents == null || parents.size() == 0){
                parents = new ArrayList<>();
                prev = DtoUtil.comment2VproComment(comment);
            } else {
                prev = parents.get(parents.size() - 1);
            }
            if (vproComment.getVproCommentId().equals(prev.getVproCommentReplyId())) {
                o = vproComment;
                parents.add(vproComment);
                comment.setParents(parents);
                if (vproComment.getVproCommentReplyId() > 0) this.iterCommentParentRelation(comment);
                break;
            }
        }
        return comment;
    }

    @Override
    public void commentSupportRate(CommentRate commentRate) {
        logger.info(commentRate.toString());
        if (commentRate.getCommentOppose() == commentRate.getCommentAgree()) return;
        logger.info("CommonOppose check passed...");
        if (commentDao.checkCommentIfExistInRedis(commentRate.getCommentId(), commentRate.getLessonId())) return;
        logger.info("comment rate set ready...");
        commentDao.setSupportRateForComment(commentRate);
    }
}
