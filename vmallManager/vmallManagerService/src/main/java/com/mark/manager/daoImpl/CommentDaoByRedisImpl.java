package com.mark.manager.daoImpl;

import com.mark.common.exception.CommentException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.pojo.VproComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("commentRedis")
public class CommentDaoByRedisImpl extends CommentDaoAbstract {
    @Autowired
    JedisClient jedisClient;

    @Value("${commentPrefix}")
    String commentPrefix;

    @Value("${commentListPrefix}")
    String commentListPrefix;

    /**
     * 将评论数据发给放到redis的VproCommentList中，消息队列处理
     * 消息队列在将评论放到mysql的同时，需要将评论放入redis，供前端读取
     * 2个数据，1个是回复评论用的维护hash表，一个是list类型的每节课的评论表，用于评论显示和分页
     *
     * 根据该条评论是否是回复：
     *      是回复，hget(comment.hash.xxx, commentReplyId), 得到结果[comment_id: xxx, ..., parent: [[],[],...]]
     *          将该结果parent外的信息放到parent里面，全部成为新评论的parent
     *          然后塞进新评论的parent中
     *          将新评论放入hash表comment.hash.LESSON_ID hkey: commentId, hvalue: 以上结果
     *      不是回复：
     *          直接将评论放到comment.hash.LESSON_ID
     *
     * 将最新的评论塞进hash表comment.hash.LESSON_ID 以及 list表comment.list.LESSON_ID
     *
     * 如果以上comment.hash.LESSON_ID 以及 list表comment.list.LESSON_ID没有，那么直接塞进数据库就完事儿了，因为前台访问的时候会生成
     * -----------------------------------------------------------------------------------------------------------------
     * 关于评论的分页，思路：
     * 按照每节课的粒度区分：
     *      1. 按照lesson_id区分
     *
     * 每节课，按照页的粒度来分缓存
     *      1.  首次生成缓存时，一口气读取某节课下的所有评论
     *      2.  根据依赖关系生成每一条评论放入array中。
     *      3.  循环数组，将数组中所有元素统统放入redis的VproCommentList。
     *      4.  将数组推进list中，用于分页，使用lrange根据llen分页。
     *      5.  后期可以定期指定crond任务，如果list非常大，那么势必需要切分
     *
     */
    @Override
    public VproComment setComment(VproComment vproComment) throws CommentException {
        String c = jedisClient.hget(commentPrefix + String.valueOf(vproComment.getVproCommentLessonId()), String.valueOf(vproComment.getVproCommentReplyId()));
        if (c == null) throw new CommentException("reply comment not exist");
        Comment comment = BeanUtil.parseJsonToObj(c, Comment.class);
        if (comment == null) throw new CommentException("parse json to Comment failed");
        VproComment parent = DtoUtil.comment2VproComment(comment);
        List<VproComment> parents = comment.getParents();
        parents.add(parent);
        Comment res = DtoUtil.vproComment2Comment(vproComment);
        res.setParents(parents);
        jedisClient.hset(commentPrefix + String.valueOf(vproComment.getVproCommentLessonId()), String.valueOf(vproComment.getVproCommentId()), BeanUtil.parseObjToJson(res));
        jedisClient.lpush(commentListPrefix + String.valueOf(vproComment.getVproCommentLessonId()), BeanUtil.parseObjToJson(res));
        return vproComment;
    }
}
