package com.mark.manager.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

public class CourseUpdate implements Serializable {
    @NotNull
    @Digits(integer = 10, fraction = 0)
    private Integer courseId;
    private Integer coursePid;
    private String courseTitle;
    private BigDecimal coursePrice;
    private BigDecimal courseDiscountPrice;
    private Integer courseStatus;
    private Integer courseCover;
    private StringBuffer courseContent;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public BigDecimal getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(BigDecimal coursePrice) {
        this.coursePrice = coursePrice;
    }

    public BigDecimal getCourseDiscountPrice() {
        return courseDiscountPrice;
    }

    public void setCourseDiscountPrice(BigDecimal courseDiscountPrice) {
        this.courseDiscountPrice = courseDiscountPrice;
    }

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
        this.courseStatus = courseStatus;
    }

    public StringBuffer getCourseContent() {
        return courseContent;
    }

    public void setCourseContent(StringBuffer courseContent) {
        this.courseContent = courseContent;
    }

    public Integer getCourseCover() {
        return courseCover;
    }

    public void setCourseCover(Integer courseCover) {
        this.courseCover = courseCover;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCoursePid() {
        return coursePid;
    }

    public void setCoursePid(Integer coursePid) {
        this.coursePid = coursePid;
    }

    public CourseUpdate() {
    }

    @Override
    public String toString() {
        return "CourseUpdate{" +
                "courseId=" + courseId +
                ", coursePid=" + coursePid +
                ", courseTitle='" + courseTitle + '\'' +
                ", coursePrice=" + coursePrice +
                ", courseDiscountPrice=" + courseDiscountPrice +
                ", courseStatus=" + courseStatus +
                ", courseCover=" + courseCover +
                ", courseContent=" + courseContent +
                '}';
    }
}
