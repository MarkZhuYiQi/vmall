package com.mark.common.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchResult implements Serializable{
    private List<Map<String, Object>> queries;
    private Long count;

    public SearchResult() {
    }

    public List<Map<String, Object>> getQueries() {
        return queries;
    }

    public void setQueries(List<Map<String, Object>> queries) {
        this.queries = queries;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

