package com.mark.manager.dto;

import com.mark.manager.pojo.VproCartDetail;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private Long cartId;
    private Long cartAddTime;
    private Integer cartUserId;
    private Boolean cartStatus;
    private Boolean cartPayment;
    private List<VproCartDetail> cartDetails;

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", cartAddTime=" + cartAddTime +
                ", cartUserId=" + cartUserId +
                ", cartStatus=" + cartStatus +
                ", cartPayment=" + cartPayment +
                ", cartDetails=" + cartDetails +
                '}';
    }

    public Cart() {
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartAddTime() {
        return cartAddTime;
    }

    public void setCartAddTime(Long cartAddTime) {
        this.cartAddTime = cartAddTime;
    }

    public Integer getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(Integer cartUserId) {
        this.cartUserId = cartUserId;
    }

    public Boolean getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(Boolean cartStatus) {
        this.cartStatus = cartStatus;
    }

    public Boolean getCartPayment() {
        return cartPayment;
    }

    public void setCartPayment(Boolean cartPayment) {
        this.cartPayment = cartPayment;
    }

    public List<VproCartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<VproCartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
