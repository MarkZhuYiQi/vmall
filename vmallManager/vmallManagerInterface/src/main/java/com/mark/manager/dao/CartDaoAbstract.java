package com.mark.manager.dao;

import com.mark.common.exception.CartException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CartDaoAbstract implements CartDao {
    @Autowired
    JedisClient jedisClient;
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
    public String getNewCartId() {
        return null;
    }

    @Override
    public VproAuth getLoginInfo(String token) {
        return null;
    }

    @Override
    public String getCartIdByUserId(Integer userId) throws CartException {
        return null;
    }
    @Override
    public void setCartIdWithUserId(String userId, String cartId) {
    }
}
