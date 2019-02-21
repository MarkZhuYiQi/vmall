package com.mark.manager.dao;

import com.mark.common.exception.CartException;
import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;

public interface CartDao {
    void createUserCart(String cartId, String token);
    void createUserCart(String cartId, VproAuth auth);
    Cart loadUserCart(String cartId) throws CartException;
    Cart addItem();
    String getNewCartId();
    VproAuth getLoginInfo(String token);
    String getCartIdByUserId(Integer userId) throws CartException;
    void setCartIdWithUserId(String userId, String cartId);
}
