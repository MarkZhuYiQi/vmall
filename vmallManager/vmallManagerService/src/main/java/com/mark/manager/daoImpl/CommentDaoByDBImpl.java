package com.mark.manager.daoImpl;

import com.mark.common.exception.CommentException;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.mapper.VproCommentMapper;
import com.mark.manager.pojo.VproComment;
import com.mark.manager.pojo.VproCommentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("commentDB")
public class CommentDaoByDBImpl extends CommentDaoAbstract {
    @Autowired
    VproCommentMapper vproCommentMapper;
    @Override
    public List<VproComment> getCommentsByLessonId(Integer lessonId) throws CommentException {
        try {
            VproCommentExample vproCommentExample = new VproCommentExample();
            vproCommentExample.createCriteria().andVproCommentLessonIdEqualTo(lessonId);
            vproCommentExample.setOrderByClause("vpro_comment_time desc");
            return vproCommentMapper.selectByExample(vproCommentExample);
        } catch (Exception e) {
            throw new CommentException(e.getMessage());
        }

    }

    @Override
    public VproComment setComment(VproComment vproComment) throws CommentException {
        try {
            // 插入评论到数据库，返回插入的主键
            Integer res = vproCommentMapper.insertSelective(vproComment);
            if (res <= 0) throw new CommentException("insert comment to db failed");
            // 获得插入的数据
            VproComment com = vproCommentMapper.selectByPrimaryKey(res);
            return com;
        } catch (Exception e) {
            throw new CommentException("insert comment to db failed" + e.getMessage());
        }
    }

    @Override
    public Boolean checkCommentsIfExistInDB(Integer lessonId) {
        VproCommentExample vproCommentExample = new VproCommentExample();
        vproCommentExample.createCriteria().andVproCommentLessonIdEqualTo(lessonId);
        Long res = vproCommentMapper.countByExample(vproCommentExample);
        return res > 0;
    }

}
