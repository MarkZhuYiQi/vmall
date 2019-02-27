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
}
