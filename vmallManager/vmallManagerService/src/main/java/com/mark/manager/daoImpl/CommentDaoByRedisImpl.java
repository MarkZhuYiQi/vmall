package com.mark.manager.daoImpl;

import com.mark.common.constant.CommentConstant;
import com.mark.common.exception.CommentException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.bo.CommentResult;
import com.mark.manager.dao.CommentDaoAbstract;
import com.mark.manager.dto.Comment;
import com.mark.manager.dto.CommentRate;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.pojo.VproComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;

@Component("commentRedis")
public class CommentDaoByRedisImpl extends CommentDaoAbstract {
    private Logger logger = LoggerFactory.getLogger(CommentDaoByRedisImpl.class);
    @Autowired
    JedisClient jedisClient;

    @Value("${commentPrefix}")
    String commentPrefix;

    @Value("${commentListPrefix}")
    String commentListPrefix;

    @Value("${commentAgreePrefix}")
    String commentAgreePrefix;

    @Value("${commentOpposePrefix}")
    String commentOpposePrefix;

    @Value("${commentAgreeListPrefix}")
    String commentAgreeListPrefix;

    @Value("${commentOpposeListPrefix}")
    String commentOpposeListPrefix;

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
     *      1. 按照lessonId区分
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
        Comment res = DtoUtil.vproComment2Comment(vproComment);
        if (vproComment.getVproCommentReplyId() > 0) {
            // 评论是回复：
            // 从redis获得想要回复的评论实体，从comment.hash.LESSONID中获得这条评论
            String c = jedisClient.hget(commentPrefix + String.valueOf(vproComment.getVproCommentLessonId()), String.valueOf(vproComment.getVproCommentReplyId()));
            if (c == null) throw new CommentException("reply comment not exist");
            Comment comment = BeanUtil.parseJsonToObj(c, Comment.class);
            if (comment == null) throw new CommentException("parse json to Comment failed");
            VproComment parent = DtoUtil.comment2VproComment(comment);
            // 拿到这条评论回复之前的评论
            List<VproComment> parents = comment.getParents();
            // 将这条评论放到父级回复的第一条
            parents.add(0, parent);
            res.setParents(parents);
        }
        jedisClient.hset(commentPrefix + String.valueOf(vproComment.getVproCommentLessonId()), String.valueOf(vproComment.getVproCommentId()), BeanUtil.parseObjToJson(res));
        jedisClient.lpush(commentListPrefix + String.valueOf(vproComment.getVproCommentLessonId()), BeanUtil.parseObjToJson(res));
        return vproComment;
    }

    @Override
    public CommentResult getCommentsForShowByLessonId(Integer lessonId, Integer pageNum, Integer pageSize) throws CommentException {
        CommentResult commentResult = new CommentResult();
        String listKey = commentListPrefix + String.valueOf(lessonId);
        // list不存在，两种情况，这文章就没有评论，或者还未生成。
        if (!jedisClient.exists(listKey)) throw new CommentException("comment has not generate yet", CommentConstant.COMMENT_LIST_NOT_EXIST_IN_REDIS);
        Integer start = (pageNum - 1) * pageSize;
        Integer end = pageNum * pageSize;
        Long total = jedisClient.llen(listKey);
        // 选择评论范围中起始值比list总长度还要多，页码有问题，无法访问到。（恶意访问）
        if (total <= start.longValue()) throw new CommentException("comment out of range", CommentConstant.COMMENT_GAIN_OUT_OF_RANGE);
        commentResult.setTotal(total);
        List<String> commentsStr = jedisClient.lrange(listKey, start, end);
        List<Comment> commentList = new ArrayList<>();
        for (String s : commentsStr) {
            commentList.add(BeanUtil.parseJsonToObj(s, Comment.class));
        }
        commentResult.setCommentList(commentList);
        commentResult.setPageNum(pageNum);
        commentResult.setPageSize(pageSize);
        logger.info("generate comments list: " + commentResult.toString());
        return commentResult;
    }

    /**
     * 仅仅当评论没有需要生成第一次的时候才会调用这个方法
     * @param comments
     * @param lessonId
     * @return
     */
    @Override
    public void setCommentsByLessonId(List<Comment> comments, Integer lessonId) {
        JedisPool jedisPool = jedisClient.getJedisPool();
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
        String[] commentsStr = new String[comments.size()];
        for  (int i = 0; i < comments.size(); i++) {
            p.hset(commentPrefix + String.valueOf(lessonId), String.valueOf(comments.get(i).getVproCommentId()), BeanUtil.parseObjToJson(comments.get(i)));
            commentsStr[i] = BeanUtil.parseObjToJson(comments.get(i));
        }
        p.lpush(commentListPrefix + String.valueOf(lessonId), commentsStr);
        p.sync();
        jedis.close();
    }

    @Override
    public Boolean checkCommentIfExistInRedis(Integer commentId, Integer lessonId) {
        logger.info(commentPrefix + String.valueOf(lessonId));
        return jedisClient.hexists(commentPrefix + String.valueOf(lessonId), String.valueOf(commentId));
    }

    @Override
    public void setSupportRateForComment(CommentRate commentRate) {
        String rateKey;
        String rateList;
        if (commentRate.getCommentAgree()) {
            rateKey = commentAgreePrefix;
            rateList = commentAgreeListPrefix;
        } else {
            rateKey = commentOpposePrefix;
            rateList = commentAgreeListPrefix;
        }
        jedisClient.lpush(rateList, String.valueOf(commentRate.getCommentId()));
        jedisClient.hincrBy(rateKey, String.valueOf(commentRate.getCommentId()), 1L);
    }
}
