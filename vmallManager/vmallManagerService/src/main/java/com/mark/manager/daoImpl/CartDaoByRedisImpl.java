package com.mark.manager.daoImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.VproAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("cartDB")
public class CartDaoByRedisImpl extends CartDaoAbstract {

    @Autowired
    private VproCartMapper vproCartMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${cartIdINCR}")
    String cartIdINCR;

    @Value("${loginInfoPrefix}")
    String loginInfoPrefix;

    @Value("${userCartIdHash}")
    String userCartIdHash;

    /**
     * 创建一个用户id和购物车id对应关系到redis
     * @param cartId
     * @param auth
     */
    @Override
    public void createUserCart(String cartId, VproAuth auth) {
        jedisClient.hset(userCartIdHash, String.valueOf(auth.getAuthId()), cartId);
    }

    @Override
    public Cart addItem() {
        return null;
    }

    public String getCartId() {
        return String.valueOf(jedisClient.incr(cartIdINCR));
    }
    public VproAuth getLoginInfo(String token) {
        Map<String, String> info = jedisClient.hgetAll(loginInfoPrefix + token);
        return  BeanUtil.mapToBean(info, VproAuth.class);
    }
}
