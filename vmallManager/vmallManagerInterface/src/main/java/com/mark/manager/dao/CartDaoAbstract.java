package com.mark.manager.dao;

import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;

public abstract class CartDaoAbstract implements CartDao {
    /**
     * Dao业务逻辑层
     * @param cartId
     * @param token
     */
    @Override
    public void createUserCart(String cartId, String token) {
    }
    /**
     * 实际数据操作层
     * @param cartId
     * @param auth
     */
    @Override
    public void createUserCart(String cartId, VproAuth auth) {
    }

    @Override
    public Cart addItem() {
        return null;
    }

    @Override
    public String getCartId() {
        return null;
    }

    @Override
    public VproAuth getLoginInfo(String token) {
        return null;
    }
}
