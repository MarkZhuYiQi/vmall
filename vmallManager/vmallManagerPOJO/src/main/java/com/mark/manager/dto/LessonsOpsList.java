package com.mark.manager.dto;

import java.io.Serializable;
import java.util.List;

public class LessonsOpsList implements Serializable {
    private List<LessonsOps> lessonsOpsList;

    public LessonsOpsList() {
    }

    public List<LessonsOps> getLessonsOpsList() {
        return lessonsOpsList;
    }

    public void setLessonsOpsList(List<LessonsOps> lessonsOpsList) {
        this.lessonsOpsList = lessonsOpsList;
    }
}
