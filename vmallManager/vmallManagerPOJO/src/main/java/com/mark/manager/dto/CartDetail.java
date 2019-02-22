package com.mark.manager.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CartDetail implements Serializable {
    @NotNull(message = "商品课程id不能为空！")
    private Long cartCourseId;
    @NotNull(message = "购物车id不能为空！")
    private Long cartParentId;
    private String cartAddTime;
    @NotNull(message = "购物车所属类型不能为空！")
    private Boolean cartIsCookie;

    @Override
    public String toString() {
        return "CartDetail{" +
                "cartCourseId=" + cartCourseId +
                ", cartParentId=" + cartParentId +
                ", cartAddTime='" + cartAddTime + '\'' +
                ", cartIsCookie=" + cartIsCookie +
                '}';
    }

    public Long getCartCourseId() {
        return cartCourseId;
    }

    public void setCartCourseId(Long cartCourseId) {
        this.cartCourseId = cartCourseId;
    }

    public Long getCartParentId() {
        return cartParentId;
    }

    public void setCartParentId(Long cartParentId) {
        this.cartParentId = cartParentId;
    }

    public String getCartAddTime() {
        return cartAddTime;
    }

    public void setCartAddTime(String cartAddTime) {
        this.cartAddTime = cartAddTime;
    }

    public Boolean getCartIsCookie() {
        return cartIsCookie;
    }

    public void setCartIsCookie(Boolean cartIsCookie) {
        this.cartIsCookie = cartIsCookie;
    }

    public CartDetail() {
    }
}
