package com.mark.manager.dto;

import java.io.Serializable;
import java.util.Arrays;

public class SearchItem implements Serializable {
    private String courseTitle;
    private String authAppid;
    private Integer coursePid;
    private Integer[] coursePids;

    public Integer[] getCoursePids() {
        return coursePids;
    }

    public void setCoursePids(Integer[] coursePids) {
        this.coursePids = coursePids;
    }

    public Integer getCoursePid() {
        return coursePid;
    }

    public void setCoursePid(Integer coursePid) {
        this.coursePid = coursePid;
    }

    public SearchItem() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getAuthAppid() {
        return authAppid;
    }

    public void setAuthAppid(String authAppid) {
        this.authAppid = authAppid;
    }

    @Override
    public String toString() {
        return "SearchItem{" +
                "courseTitle='" + courseTitle + '\'' +
                ", authAppid='" + authAppid + '\'' +
                ", coursePid=" + coursePid +
                '}';
    }
}
