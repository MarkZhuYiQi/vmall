package com.mark.manager.mapper;

import com.mark.manager.dto.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    Cart loadUserCart(
            @Param("cartId") String cartId,
            @Param("userId") String userId
    );
    List<String> compareCartItemsWithCookieCartAndUserCart(
            @Param("cartId") String cartId,
            @Param("cookieCartId") String cookieCartId
    );
}
