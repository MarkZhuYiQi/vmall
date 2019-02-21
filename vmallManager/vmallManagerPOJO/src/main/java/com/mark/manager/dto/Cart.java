package com.mark.manager.dto;

import com.mark.manager.pojo.VproCartDetail;

import java.util.List;

public class Cart {
    private Long cartId;
    private Long cartAddTime;
    private Integer cartUserId;
    private Boolean cartStatus;
    private Boolean cartPayment;
    private List<VproCartDetail> cartDetails;
}
