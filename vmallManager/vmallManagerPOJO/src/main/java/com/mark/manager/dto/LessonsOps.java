package com.mark.manager.dto;

import com.mark.manager.pojo.VproCoursesLessonList;

import java.io.Serializable;

public class LessonsOps implements Serializable {
    private Integer courseId;
    private byte type;
    private Integer ops;
    private byte isTitle;
    private VproCoursesLessonList original;
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
}
