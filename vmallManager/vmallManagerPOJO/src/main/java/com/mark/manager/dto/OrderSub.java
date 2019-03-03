package com.mark.manager.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderSub implements Serializable {
    private Integer orderSubId;

    private Long orderId;

    private Integer courseId;

    private Integer userId;

    private BigDecimal coursePrice;

    private String courseTitle;

    private String courseAuthor;

    private String courseCover;

    public OrderSub() {
    }

    @Override
    public String toString() {
        return "OrderSub{" +
                "orderSubId=" + orderSubId +
                ", orderId=" + orderId +
                ", courseId=" + courseId +
                ", coursePrice=" + coursePrice +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseAuthor='" + courseAuthor + '\'' +
                ", courseCover='" + courseCover + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(Integer orderSubId) {
        this.orderSubId = orderSubId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(BigDecimal coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseAuthor() {
        return courseAuthor;
    }

    public void setCourseAuthor(String courseAuthor) {
        this.courseAuthor = courseAuthor;
    }

    public String getCourseCover() {
        return courseCover;
    }

    public void setCourseCover(String courseCover) {
        this.courseCover = courseCover;
    }
}