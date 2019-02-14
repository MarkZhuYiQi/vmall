package com.mark.manager.dto;

public class Lesson {
    private Integer lessonId;
    private Integer lessonLid;
    private Integer lessonPid;
    private String lessonTitle;
    private Boolean lessonIsChapterHead;
    private Integer lessonCourseId;
    private Boolean lessonIsDeleted;

    public Lesson() {
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", lessonLid=" + lessonLid +
                ", lessonPid=" + lessonPid +
                ", lessonTitle='" + lessonTitle + '\'' +
                ", lessonIsChapterHead=" + lessonIsChapterHead +
                ", lessonCourseId=" + lessonCourseId +
                ", lessonIsDeleted=" + lessonIsDeleted +
                '}';
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getLessonLid() {
        return lessonLid;
    }

    public void setLessonLid(Integer lessonLid) {
        this.lessonLid = lessonLid;
    }

    public Integer getLessonPid() {
        return lessonPid;
    }

    public void setLessonPid(Integer lessonPid) {
        this.lessonPid = lessonPid;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public Boolean getLessonIsChapterHead() {
        return lessonIsChapterHead;
    }

    public void setLessonIsChapterHead(Boolean lessonIsChapterHead) {
        this.lessonIsChapterHead = lessonIsChapterHead;
    }

    public Integer getLessonCourseId() {
        return lessonCourseId;
    }

    public void setLessonCourseId(Integer lessonCourseId) {
        this.lessonCourseId = lessonCourseId;
    }

    public Boolean getLessonIsDeleted() {
        return lessonIsDeleted;
    }

    public void setLessonIsDeleted(Boolean lessonIsDeleted) {
        this.lessonIsDeleted = lessonIsDeleted;
    }
}
