package com.mark.manager.dao;

import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.pojo.VproAuth;
import com.mark.common.exception.CartException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.pojo.VproCartDetail;
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
    public void createUserCart(String cartId, String token) throws CartException {
    }
    /**
     * 实际数据操作层
     * @param cartId
     * @param auth
     */
    @Override
    public void createUserCart(String cartId, VproAuth auth) throws CartException {
    }

    @Override
    public VproCartDetail addItem(CartDetail cartDetail) throws CartException {
        return null;
    }

    @Override
    public String getNewCartId() {
        return null;
    }

    @Override
    public VproAuth getLoginInfo(String token) throws CartException {
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
