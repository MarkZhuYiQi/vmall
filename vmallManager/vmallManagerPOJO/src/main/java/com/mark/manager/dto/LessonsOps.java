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
     * up: 1
     * down: 2
     */
    private Integer type;
    /**
     * 放置位置类型
     * 1： before，在该对象的上面
     * 2： after, 在该对象下面
     * 3： inner，在该对象的里面（追加到对象children的尾部）
     */
    private Integer dropType;
    /**
     * 具体的操作类型
     * 	ops: {
     * 		101: "add lesson",
     * 		102: "move lesson",
     * 		103: "delete lesson",
     * 		201: "add subtitle",
     * 		202: "move subtitle",
     * 		203: "delete subtitle",
     *        },
     */
    private Integer ops;
    /**
     * 是否是对标题的控制
     */
    private Integer isTitle;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDropType() {
        return dropType;
    }

    public void setDropType(Integer dropType) {
        this.dropType = dropType;
    }

    public Integer getOps() {
        return ops;
    }

    public void setOps(Integer ops) {
        this.ops = ops;
    }

    public Integer getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(Integer isTitle) {
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
