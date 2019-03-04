package com.mark.manager.dto;

import com.mark.manager.pojo.VproComment;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Comment implements Serializable {

    private Integer vproCommentId;

    private String vproCommentContent;

    private Integer vproCommentUserId;

    private Integer vproCommentCourseId;

    private Integer vproCommentLessonId;

    private Integer vproCommentReplyId;

    private Integer vproCommentReplyMainId;

    private Boolean vproCommentIsPublished;

    private String vproCommentTime;

    private List<VproComment> parents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(vproCommentId, comment.vproCommentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vproCommentId, vproCommentContent, vproCommentUserId, vproCommentCourseId, vproCommentLessonId, vproCommentReplyId, vproCommentReplyMainId, vproCommentIsPublished, vproCommentTime, parents);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "vproCommentId=" + vproCommentId +
                ", vproCommentContent='" + vproCommentContent + '\'' +
                ", vproCommentUserId=" + vproCommentUserId +
                ", vproCommentCourseId=" + vproCommentCourseId +
                ", vproCommentLessonId=" + vproCommentLessonId +
                ", vproCommentReplyId=" + vproCommentReplyId +
                ", vproCommentReplyMainId=" + vproCommentReplyMainId +
                ", vproCommentIsPublished=" + vproCommentIsPublished +
                ", vproCommentTime='" + vproCommentTime + '\'' +
                ", parents=" + parents +
                '}';
    }

    public Integer getVproCommentId() {
        return vproCommentId;
    }

    public void setVproCommentId(Integer vproCommentId) {
        this.vproCommentId = vproCommentId;
    }

    public String getVproCommentContent() {
        return vproCommentContent;
    }

    public void setVproCommentContent(String vproCommentContent) {
        this.vproCommentContent = vproCommentContent;
    }

    public Integer getVproCommentUserId() {
        return vproCommentUserId;
    }

    public void setVproCommentUserId(Integer vproCommentUserId) {
        this.vproCommentUserId = vproCommentUserId;
    }

    public Integer getVproCommentCourseId() {
        return vproCommentCourseId;
    }

    public void setVproCommentCourseId(Integer vproCommentCourseId) {
        this.vproCommentCourseId = vproCommentCourseId;
    }

    public Integer getVproCommentLessonId() {
        return vproCommentLessonId;
    }

    public void setVproCommentLessonId(Integer vproCommentLessonId) {
        this.vproCommentLessonId = vproCommentLessonId;
    }

    public Integer getVproCommentReplyId() {
        return vproCommentReplyId;
    }

    public void setVproCommentReplyId(Integer vproCommentReplyId) {
        this.vproCommentReplyId = vproCommentReplyId;
    }

    public Integer getVproCommentReplyMainId() {
        return vproCommentReplyMainId;
    }

    public void setVproCommentReplyMainId(Integer vproCommentReplyMainId) {
        this.vproCommentReplyMainId = vproCommentReplyMainId;
    }

    public Boolean getVproCommentIsPublished() {
        return vproCommentIsPublished;
    }

    public void setVproCommentIsPublished(Boolean vproCommentIsPublished) {
        this.vproCommentIsPublished = vproCommentIsPublished;
    }

    public String getVproCommentTime() {
        return vproCommentTime;
    }

    public void setVproCommentTime(String vproCommentTime) {
        this.vproCommentTime = vproCommentTime;
    }

    public List<VproComment> getParents() {
        return parents;
    }

    public void setParents(List<VproComment> parents) {
        this.parents = parents;
    }
}
