package com.mark.manager.bo;

import com.mark.manager.dto.Comment;

import java.io.Serializable;
import java.util.List;

public class CommentResult implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<Comment> commentList;

    public CommentResult() {
    }

    @Override
    public String toString() {
        return "CommentResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", commentList=" + commentList +
                '}';
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
