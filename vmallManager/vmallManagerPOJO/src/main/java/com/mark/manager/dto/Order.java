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
    private Boolean orderPayment;
    private String orderTitle;
    private String orderPaymentId;
    private String orderPaymentPrice;
    private List<VproOrderSub> vproOrderSubs;

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

    public Boolean getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(Boolean orderPayment) {
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

    public List<VproOrderSub> getVproOrderSubs() {
        return vproOrderSubs;
    }

    public void setVproOrderSubs(List<VproOrderSub> vproOrderSubs) {
        this.vproOrderSubs = vproOrderSubs;
    }
}
