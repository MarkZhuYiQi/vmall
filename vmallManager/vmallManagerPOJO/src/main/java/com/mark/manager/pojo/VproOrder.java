package com.mark.manager.pojo;

import java.math.BigDecimal;

public class VproOrder {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private Long orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private BigDecimal orderPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_time
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private String orderTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.user_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_coupon_used
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private Integer orderCouponUsed;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_discount
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private Integer orderDiscount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_payment
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private Boolean orderPayment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_title
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private String orderTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_payment_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private String orderPaymentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vpro_order.order_payment_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    private BigDecimal orderPaymentPrice;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_id
     *
     * @return the value of vpro_order.order_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_id
     *
     * @param orderId the value for vpro_order.order_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_price
     *
     * @return the value of vpro_order.order_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_price
     *
     * @param orderPrice the value for vpro_order.order_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_time
     *
     * @return the value of vpro_order.order_time
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_time
     *
     * @param orderTime the value for vpro_order.order_time
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.user_id
     *
     * @return the value of vpro_order.user_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.user_id
     *
     * @param userId the value for vpro_order.user_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_coupon_used
     *
     * @return the value of vpro_order.order_coupon_used
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public Integer getOrderCouponUsed() {
        return orderCouponUsed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_coupon_used
     *
     * @param orderCouponUsed the value for vpro_order.order_coupon_used
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderCouponUsed(Integer orderCouponUsed) {
        this.orderCouponUsed = orderCouponUsed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_discount
     *
     * @return the value of vpro_order.order_discount
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public Integer getOrderDiscount() {
        return orderDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_discount
     *
     * @param orderDiscount the value for vpro_order.order_discount
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderDiscount(Integer orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_payment
     *
     * @return the value of vpro_order.order_payment
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public Boolean getOrderPayment() {
        return orderPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_payment
     *
     * @param orderPayment the value for vpro_order.order_payment
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderPayment(Boolean orderPayment) {
        this.orderPayment = orderPayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_title
     *
     * @return the value of vpro_order.order_title
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public String getOrderTitle() {
        return orderTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_title
     *
     * @param orderTitle the value for vpro_order.order_title
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_payment_id
     *
     * @return the value of vpro_order.order_payment_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public String getOrderPaymentId() {
        return orderPaymentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_payment_id
     *
     * @param orderPaymentId the value for vpro_order.order_payment_id
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderPaymentId(String orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vpro_order.order_payment_price
     *
     * @return the value of vpro_order.order_payment_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public BigDecimal getOrderPaymentPrice() {
        return orderPaymentPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vpro_order.order_payment_price
     *
     * @param orderPaymentPrice the value for vpro_order.order_payment_price
     *
     * @mbg.generated Wed Feb 27 17:28:28 CST 2019
     */
    public void setOrderPaymentPrice(BigDecimal orderPaymentPrice) {
        this.orderPaymentPrice = orderPaymentPrice;
    }
}