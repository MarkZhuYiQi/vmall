package com.mark.manager.serviceImpl;

import com.mark.common.exception.CommentException;
import com.mark.manager.dao.CommentDao;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.pojo.VproComment;
import com.mark.manager.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class commentServiceImpl implements CommentService {

    @Autowired
    @Qualifier("commentDao")
    CommentDao commentDao;

    private List<VproComment> data;

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
    private Comment iterCommentParentRelation(Comment comment) {
        VproComment o = null;
        for (VproComment vproComment : data) {
            if (vproComment.getVproCommentId().equals(comment.getVproCommentReplyId())) {
                o = vproComment;
                List<Comment> parents = new ArrayList<>();
                parents = comment.getParents();
                parents.add(DtoUtil.vproComment2Comment(vproComment));
                comment.setParents(parents);
                if (vproComment.getVproCommentReplyId() > 0) this.iterCommentParentRelation(comment);
            }
            break;
        }
        if (o != null) this.data.remove(o);
        return comment;
    }
}
