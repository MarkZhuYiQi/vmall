package com.mark.manager.dto;

import java.io.Serializable;

public class Search implements Serializable {
    private String type;
    private Integer start;
    private Integer size;
    private Long startDate;
    private Long endDate;
    private SearchItem searchItem;

    public Search() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public SearchItem getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(SearchItem searchItem) {
        this.searchItem = searchItem;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Search{" +
                "type='" + type + '\'' +
                ", start=" + start +
                ", size=" + size +
                ", searchItem=" + searchItem +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
