package com.mark.manager.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class OrderCriteria implements Serializable {
    private Integer userId;
    @NotEmpty( message = "未指定任何订单状态信息")
    private Integer orderPayment;
    @NotEmpty( message = "订单页码不能为空")
    private Integer pageNum;
    @NotEmpty( message = "页面容量不能为空")
    private Integer pageSize;

    @Override
    public String toString() {
        return "OrderCriteria{" +
                "userId=" + userId +
                ", orderPayment=" + orderPayment +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }

    public OrderCriteria() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Integer orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Integer getpageNum() {
        return pageNum;
    }

    public void setpageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
