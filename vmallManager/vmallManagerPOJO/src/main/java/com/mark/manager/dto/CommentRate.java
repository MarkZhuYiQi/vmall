package com.mark.manager.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CommentRate implements Serializable {
    @NotNull(message = "评论id不能为空")
    private Integer commentId;
    @NotNull(message = "lesson id不能为空")
    private Integer lessonId;
    private Boolean commentAgree;
    private Boolean commentOppose;

    @Override
    public String toString() {
        return "CommentRate{" +
                "commentId=" + commentId +
                ", lessonId=" + lessonId +
                ", commentAgree=" + commentAgree +
                ", commentOppose=" + commentOppose +
                '}';
    }

    public CommentRate() {
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Boolean getCommentAgree() {
        return commentAgree;
    }

    public void setCommentAgree(Boolean commentAgree) {
        this.commentAgree = commentAgree;
    }

    public Boolean getCommentOppose() {
        return commentOppose;
    }

    public void setCommentOppose(Boolean commentOppose) {
        this.commentOppose = commentOppose;
    }
}
