package com.mark.manager.dto;

import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;

public class LessonsOps implements Serializable {
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 上移还是下移
     */
    private byte type;
    /**
     * 具体的操作类型
     */
    private Integer ops;
    /**
     * 是否是对标题的控制
     */
    private byte isTitle;
    /**
     * 源位置，即被调整的lesson
     */
    private VproCoursesLessonList original;
    /**
     * 目标位置，被替换的目标位置lesson
     */
    private VproCoursesLessonList destination;

    public LessonsOps() {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Integer getOps() {
        return ops;
    }

    public void setOps(Integer ops) {
        this.ops = ops;
    }

    public byte getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(byte isTitle) {
        this.isTitle = isTitle;
    }

    public VproCoursesLessonList getOriginal() {
        return original;
    }

    public void setOriginal(VproCoursesLessonList original) {
        this.original = original;
    }

    public VproCoursesLessonList getDestination() {
        return destination;
    }

    public void setDestination(VproCoursesLessonList destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "LessonsOps{" +
                "courseId=" + courseId +
                ", type=" + type +
                ", ops=" + ops +
                ", isTitle=" + isTitle +
                ", original=" + original +
                ", destination=" + destination +
                '}';
    }
}
