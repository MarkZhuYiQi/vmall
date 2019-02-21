package com.mark.manager.daoImpl;

import com.mark.common.exception.CartException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCartDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("cartDB")
public class CartDaoByRedisImpl extends CartDaoAbstract {

    @Autowired
    private VproCartMapper vproCartMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${cartIdINCR}")
    String cartIdINCR;

    // 用户登录信息存放前缀，后加token，构成键名
    @Value("${loginInfoPrefix}")
    String loginInfoPrefix;

    // 用户id和购物车id对应表，构成hash表
    @Value("${userCartIdHash}")
    String userCartIdHash;

    // 未登录用户cookie购物车存储前缀，后加cartId，sortedSet
    @Value("${cookieCartPrefix}")
    String cookieCartPrefix;
    // 登陆用户购物车存储前缀，后加cartId，sortedSet
    @Value("${userCartPrefix}")
    String userCartPrefix;

    /**
     * 创建一个用户id和购物车id对应关系到redis
     * @param cartId
     * @param auth
     */
    @Override
    public void createUserCart(String cartId, VproAuth auth) {
        jedisClient.hset(userCartIdHash, String.valueOf(auth.getAuthId()), cartId);
    }

    /**
     * 读取用户购物车信息分2步
     * 首先根据用户id去用户id与购物车id对应表中寻找购物车id
     * 然后通过购物车id找到购物车条目Set
     * 最后在Set中取出该用户的商品信息。
     * @param cartId
     * @return
     */
    @Override
    public Cart loadUserCart(String cartId) throws CartException {
        Cart cart = new Cart();
        cart.setCartId(Long.parseLong(cartId));
        if (!jedisClient.exists(userCartPrefix + cartId)) throw new CartException("userCart could not be found in redis");
        Set<String> items = jedisClient.smembers(userCartPrefix + cartId);
        if (items.size() == 0) return cart;
        List<VproCartDetail> details = new ArrayList<>();
        for (String item : items) {
            details.add(BeanUtil.parseJsonToObj(item, VproCartDetail.class));
        }
        cart.setCartDetails(details);
        return cart;
    }

    @Override
    public Cart addItem() {
        return null;
    }

    public String getNewCartId() {
        return String.valueOf(jedisClient.incr(cartIdINCR));
    }
    public VproAuth getLoginInfo(String token) {
        Map<String, String> info = jedisClient.hgetAll(loginInfoPrefix + token);
        return  BeanUtil.mapToBean(info, VproAuth.class);
    }

    /**
     * 通过用户id从redis获取购物车id
     * @param userId
     * @return
     * @throws CartException
     */
    @Override
    public String getCartIdByUserId(Integer userId) throws CartException {
        String cartId = jedisClient.hget(userCartIdHash, String.valueOf(userId));
        if (StringUtils.isEmpty(cartId)) throw new CartException("could not get cartId from redis by userId");
        return cartId;
    }

    @Override
    public void setCartIdWithUserId(String userId, String cartId) {
        jedisClient.hset(userCartIdHash, userId, cartId);
    }
}
