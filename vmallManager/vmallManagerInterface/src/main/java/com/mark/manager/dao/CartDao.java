package com.mark.manager.dao;

import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;

public interface CartDao {
    void createUserCart(String cartId, String token);
    void createUserCart(String cartId, VproAuth auth);
    Cart loadUserCart(String cartId, VproAuth auth);
    Cart addItem();
    String getCartId();
    VproAuth getLoginInfo(String token);
}
