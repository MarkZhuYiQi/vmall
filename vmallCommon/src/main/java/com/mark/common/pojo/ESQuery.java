package com.mark.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ESQuery implements Serializable {
    private Map<String, Object> should;
    private Map<String, Object> must;
    private Map<String, Object> mustNot;
    private Map<String, Object> filter;

    public ESQuery() {
        Map<String, Object> map = new HashMap<String, Object>();
        setFilter(map);
        setMust(map);
        setMustNot(map);
        setShould(map);
    }

    public Map<String, Object> getShould() {
        return should;
    }

    public void setShould(Map<String, Object> should) {
        this.should = should;
    }

    public Map<String, Object> getMust() {
        return must;
    }

    public void setMust(Map<String, Object> must) {
        this.must = must;
    }

    public Map<String, Object> getMustNot() {
        return mustNot;
    }

    public void setMustNot(Map<String, Object> mustNot) {
        this.mustNot = mustNot;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
