package com.mark.manager.serviceImpl;

import com.mark.manager.dao.CartDao;
import com.mark.manager.dto.Cart;
import com.mark.manager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Value("${cookieCartPrefix}")
    String cookieCartPrefix;
    @Value("${userCartPrefix}")
    String userCartPrefix;
    @Autowired
    @Qualifier("cartDao")
    CartDao cartDao;
    /**
     * 取得id下的购物车信息
     * 如果已经登录了，那么会获得user与购物车id对应的id，没有登录会得到cookieid，如果啥都没有就返回空
     * 登陆状态：
     *      去redis的usercart表寻找对应用户id的购物车id，如果返回false说明还未设置，去数据库查找，如果没找到则设置-1，否则设置查找结果；
     *      然后将该cart_id: [-1|xxxxxx]返回前台，前台根据该结果决定是否要创建新的cart_id
     *      根据cartId，如果cartId有值，就去寻找购物车信息，塞入结果中
     * 未登录状态：
     *
     * @return
     */
    @Override
    public Cart loadUserCartWithLogin(String token) {
        // 没有拿到用户购物车ID，获得一个新的非重复购物车id
        String cartId = cartDao.getCartId();

        // 从缓存和数据库获得购物车
        Cart cart = cartDao.loadUserCart(cartId);

        // 都没有，就创建购物车
        cartDao.createUserCart(cartId, token);
        return null;
    }
    @Override
    public Cart loadUserCartWithoutLogin() {
        String cartNo = cart + String.valueOf(cartId);
        return null;
    }
}
