package com.mark.manager.bo;

import com.mark.common.pojo.CategoryNode;
import com.mark.manager.dto.Courses;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class IndexResult implements Serializable {
    private CategoryNode nav;
    private Map<Integer, List<Courses>> courses;

    public IndexResult() {
    }

    public CategoryNode getNav() {
        return nav;
    }

    public void setNav(CategoryNode nav) {
        this.nav = nav;
    }

    public Map<Integer, List<Courses>> getCourses() {
        return courses;
    }

    public void setCourses(Map<Integer, List<Courses>> courses) {
        this.courses = courses;
    }
}
