package com.mark.manager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class PutOrder implements Serializable {
    @NotBlank(message = "购物车ID不能为空")
    private String cartId;
    @NotBlank(message = "订单总价不能为空")
    private String orderPrice;
    private Long couponId;
    @NotNull(message = "订单商品id集合不能为空")
    private List<String> coursesId;

    @Override
    public String toString() {
        return "PutOrder{" +
                "cartId='" + cartId + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", couponId=" + couponId +
                ", coursesId=" + coursesId +
                '}';
    }

    public PutOrder() {
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public List<String> getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(List<String> coursesId) {
        this.coursesId = coursesId;
    }
}
