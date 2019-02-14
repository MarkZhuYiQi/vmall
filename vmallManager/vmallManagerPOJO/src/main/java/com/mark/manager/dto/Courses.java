package com.mark.manager.dto;

import com.mark.manager.pojo.*;
import com.mark.common.validateGroup.CreateCourse;
import com.mark.common.validateGroup.InsertCourseItems;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

public class Courses implements Serializable {
    @NotBlank(message = "课程位置错误（id）", groups = {InsertCourseItems.class})
    @Pattern(regexp = "^[0-9]+$", groups = {InsertCourseItems.class})
    private String courseId;
    @NotBlank(message = "课程名称为空", groups = {CreateCourse.class, InsertCourseItems.class})
    private String courseTitle;
    @NotNull(message = "课程归属不能为空",groups = {CreateCourse.class, InsertCourseItems.class})
    private Integer coursePid;
    @NotBlank(message = "课程作者不能为空", groups = {CreateCourse.class, InsertCourseItems.class})
    private String courseAuthor;

    private String courseCover;

    private String courseTime;

    private Integer courseStatus;
    @DecimalMin(value = "0.00", message = "课程价格不能低于0元", groups = {InsertCourseItems.class})
    private Float coursePrice;

    private Float courseDiscountPrice;

    private Float courseScore;

    private Integer courseClickNum;

    private VproCoursesCover vproCoursesCover;

    private VproAuth vproAuth;

    private VproCoursesContent vproCoursesContent;

    private VproCoursesTempDetail vproCoursesTempDetail;

    public Courses() {
    }

    @Override
    public String toString() {
        return "Courses{" +
                "courseId='" + courseId + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", coursePid=" + coursePid +
                ", courseAuthor='" + courseAuthor + '\'' +
                ", courseCover='" + courseCover + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", courseStatus=" + courseStatus +
                ", coursePrice=" + coursePrice +
                ", courseDiscountPrice=" + courseDiscountPrice +
                ", courseScore=" + courseScore +
                ", courseClickNum=" + courseClickNum +
                ", vproCoursesCover=" + vproCoursesCover +
                ", vproAuth=" + vproAuth +
                ", vproCoursesContent=" + vproCoursesContent +
                ", vproCoursesTempDetail=" + vproCoursesTempDetail +
                '}';
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

    public Float getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Float courseScore) {
        this.courseScore = courseScore;
    }

    public Integer getCourseClickNum() {
        return courseClickNum;
    }

    public void setCourseClickNum(Integer courseClickNum) {
        this.courseClickNum = courseClickNum;
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

    public VproCoursesContent getVproCoursesContent() {
        return vproCoursesContent;
    }

    public void setVproCoursesContent(VproCoursesContent vproCoursesContent) {
        this.vproCoursesContent = vproCoursesContent;
    }

    public VproCoursesTempDetail getVproCoursesTempDetail() {
        return vproCoursesTempDetail;
    }

    public void setVproCoursesTempDetail(VproCoursesTempDetail vproCoursesTempDetail) {
        this.vproCoursesTempDetail = vproCoursesTempDetail;
    }
}
