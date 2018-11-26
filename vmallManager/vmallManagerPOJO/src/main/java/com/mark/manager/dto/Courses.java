package com.mark.manager.dto;

import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCoursesCover;

import java.io.Serializable;

public class Courses implements Serializable {
    private String courseId;

    private String courseTitle;

    private Integer coursePid;

    private String courseAuthor;

    private String courseCover;

    private String courseTime;

    private Integer courseStatus;

    private Float coursePrice;

    private Float courseDiscountPrice;

    private VproCoursesCover vproCoursesCover;

    private VproAuth vproAuth;

    public Courses() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getCoursePid() {
        return coursePid;
    }

    public void setCoursePid(Integer coursePid) {
        this.coursePid = coursePid;
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

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Float getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Float coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Float getCourseDiscountPrice() {
        return courseDiscountPrice;
    }

    public void setCourseDiscountPrice(Float courseDiscountPrice) {
        this.courseDiscountPrice = courseDiscountPrice;
    }

    public VproCoursesCover getVproCoursesCover() {
        return vproCoursesCover;
    }

    public void setVproCoursesCover(VproCoursesCover vproCoursesCover) {
        this.vproCoursesCover = vproCoursesCover;
    }

    public VproAuth getVproAuth() {
        return vproAuth;
    }

    public void setVproAuth(VproAuth vproAuth) {
        this.vproAuth = vproAuth;
    }
}
