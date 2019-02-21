package com.mark.manager.daoImpl;

import com.mark.common.constant.CartConstant;
import com.mark.common.exception.CartException;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.pojo.VproAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("cartDao")
public class CartDaoImpl extends CartDaoAbstract {
    private Logger logger = LoggerFactory.getLogger(CartDaoImpl.class);
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
    public Cart loadUserCart(String cartId) throws CartException {
        try {
            return cartRedis.loadUserCart(cartId);
        } catch (CartException e) {
            logger.warn(e.getMsg());
            try {
                return cartDB.loadUserCart(cartId);
            } catch (CartException ec) {
                logger.error("userCart could not be found! " + ec.getMsg());
                throw new CartException("userCart could not be found! " + ec.getMsg(), CartConstant.CART_NOT_FOUND);
            }
        }
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
    public String getNewCartId() {
        return cartRedis.getNewCartId();
    }

    /**
     * 得到用户id对应的cartId
     * @param userId
     * @return
     * @throws CartException
     */
    @Override
    public String getCartIdByUserId(Integer userId) throws CartException {
        try {
            return cartRedis.getCartIdByUserId(userId);
        } catch (CartException ec) {
            logger.warn(ec.getMsg());
            try {
                String cartId = cartDB.getCartIdByUserId(userId);
                cartRedis.setCartIdWithUserId(String.valueOf(userId), cartId);
                return cartId;
            } catch (CartException ece) {
                logger.warn(ece.getMsg());
                throw new CartException("cartId could not be found by userId " + userId + ", " + ece.getMsg(), CartConstant.CARTID_GET_FAILED_BY_USERID);
            }
        }
    }
}
