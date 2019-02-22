package com.mark.manager.daoImpl;

import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("cartDao")
public class CartDaoImpl extends CartDaoAbstract {

    @Autowired
    @Qualifier("cartRedis")
    CartDao cartRedis;
    @Autowired
    @Qualifier("cartDB")
    CartDao cartDB;

    /**
     * 用户购物车存放方法：
     * 1. 数据库存放用户购物车信息（购物车id，用户id等）
     * 2. redis缓存：user.cart.hash 里面用户id，购物车id一一对应；userCartPrefix[cart.] + cartId: sortedSet存放用户购物车商品信息。
     */
    /**
     * 创建用户购物车
     * @param cartId
     * @param token
     */
    @Override
    public void createUserCart(String cartId, String token) {
        VproAuth auth = cartRedis.getLoginInfo(token);
        cartDB.createUserCart(cartId, auth);
        cartRedis.createUserCart(cartId, auth);
    }

    @Override
    public Cart loadUserCart(String cartId, VproAuth auth) {

    }

    @Override
    public Cart addItem() {
        return null;
    }

    /**
     * 获得一个新的购物车id
     * @return
     */
    @Override
    public String getCartId() {
        return cartRedis.getCartId();
    }
}
