package com.mark.manager.dto;

import com.mark.manager.pojo.VproOrderSub;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String orderPrice;
    private String orderTime;
    private Integer userId;
    private Boolean orderCouponUsed;
    private String orderDiscount;
    private Integer orderPayment;
    private String orderTitle;
    // 支付宝返回的trade_no
    private String orderPaymentId;
    private String orderPaymentPrice;
    private List<OrderSub> orderSubs;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", userId=" + userId +
                ", orderCouponUsed=" + orderCouponUsed +
                ", orderDiscount='" + orderDiscount + '\'' +
                ", orderPayment=" + orderPayment +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderPaymentId='" + orderPaymentId + '\'' +
                ", orderPaymentPrice='" + orderPaymentPrice + '\'' +
                ", orderSubs=" + orderSubs +
                '}';
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getOrderCouponUsed() {
        return orderCouponUsed;
    }

    public void setOrderCouponUsed(Boolean orderCouponUsed) {
        this.orderCouponUsed = orderCouponUsed;
    }

    public String getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(String orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public Integer getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Integer orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(String orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public String getOrderPaymentPrice() {
        return orderPaymentPrice;
    }

    public void setOrderPaymentPrice(String orderPaymentPrice) {
        this.orderPaymentPrice = orderPaymentPrice;
    }

    public List<OrderSub> getOrderSubs() {
        return orderSubs;
    }

    public void setOrderSubs(List<OrderSub> orderSubs) {
        this.orderSubs = orderSubs;
    }
}
