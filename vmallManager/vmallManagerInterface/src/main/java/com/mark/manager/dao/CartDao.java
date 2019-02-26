package com.mark.manager.dao;

import com.mark.common.exception.CartException;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCartDetail;

import java.util.List;

public interface CartDao {
    void createUserCart(String cartId, String token) throws CartException;
    void createUserCart(String cartId, VproAuth auth) throws CartException;
    Cart loadUserCart(String cartId, String userId) throws CartException;
    VproCartDetail addItem(CartDetail cartDetail) throws CartException;
    String getNewCartId();
    VproAuth getLoginInfo(String token) throws CartException;
    String getCartIdByUserId(Integer userId) throws CartException;
    void setCartIdWithUserId(String userId, String cartId);
    Boolean checkCartExists(String cartId) throws CartException;
    Boolean delCartItem(CartDetail cartDetail) throws CartException;
    Boolean mergeCart(String cookieCartId, String cartId) throws CartException;
}
