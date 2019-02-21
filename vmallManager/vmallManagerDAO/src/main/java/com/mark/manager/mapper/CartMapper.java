package com.mark.manager.mapper;

import com.mark.manager.dto.Cart;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    Cart loadUserCart(
            @Param("userId") Integer userId
    );
}
