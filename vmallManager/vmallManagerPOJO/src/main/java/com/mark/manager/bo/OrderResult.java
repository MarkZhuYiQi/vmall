package com.mark.manager.bo;

import com.mark.manager.dto.Order;

import java.io.Serializable;
import java.util.List;

public class OrderResult implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<Order> orders;

    @Override
    public String toString() {
        return "OrderResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", orders=" + orders +
                '}';
    }

    public OrderResult() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
