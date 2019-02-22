package com.mark.manager.mapper;

import com.mark.manager.dto.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {
    Cart loadUserCart(
            @Param("cartId") String cartId,
            @Param("userId") String userId
    );
}
