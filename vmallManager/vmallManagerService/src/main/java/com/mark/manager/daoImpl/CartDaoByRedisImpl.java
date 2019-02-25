package com.mark.manager.daoImpl;

import com.mark.common.exception.CartException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.VproAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import com.mark.manager.pojo.VproCartDetail;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("cartRedis")
public class CartDaoByRedisImpl extends CartDaoAbstract {

    private static final Logger logger = LoggerFactory.getLogger(CartDaoByRedisImpl.class);

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

    // 未登录用户cookie购物车存储前缀，后加cartId，SortedSet
    @Value("${cookieCartPrefix}")
    String cookieCartPrefix;
    // 登陆用户购物车存储前缀，后加cartId，SortedSet
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
     * 然后通过购物车id找到购物车条目SortedSet
     * 最后在SortedSet中取出该用户的商品信息。
     * @param cartId
     * @return
     */
    @Override
    public Cart loadUserCart(String cartId, String userId) throws CartException {
        Cart cart = new Cart();
        cart.setCartId(Long.parseLong(cartId));
        if (!jedisClient.exists(userCartPrefix + cartId)) throw new CartException("userCart could not be found in redis");
        Set<Tuple> items = jedisClient.zrangeWithScores(cartId, 0L, -1L);;
        if (items.size() == 0) return cart;
        List<VproCartDetail> details = new ArrayList<>();
        for (Tuple item : items) {
            details.add(BeanUtil.parseJsonToObj(item.getElement(), VproCartDetail.class));
        }
        cart.setCartDetail(details);
        return cart;
    }

    @Override
    public VproCartDetail addItem(CartDetail cartDetail) throws CartException {
        String key = (cartDetail.getCartIsCookie() ? cookieCartPrefix : userCartPrefix) + cartDetail.getCartParentId();
        if (jedisClient.zcount(key, cartDetail.getCartCourseId().doubleValue(), cartDetail.getCartCourseId().doubleValue()) > 0)
            throw new CartException("add item to userCart failed! course already existed." + cartDetail.toString());
        VproCartDetail vproCartDetail = DtoUtil.cartDetail2VproCartDetail(cartDetail);
        Long reply = jedisClient.zadd(
                key,
                cartDetail.getCartCourseId().doubleValue(),
                BeanUtil.parseObjToJson(vproCartDetail)
        );
        if (cartDetail.getCartIsCookie()) jedisClient.pexpireAt(key, JedisUtil.cookieExpireTime());
        if (reply > 0) return vproCartDetail;
        throw new CartException("add item to userCart failed! cartId: " + vproCartDetail.getCartParentId() + ", courseId: " + vproCartDetail.getCartCourseId());
    }

    public String getNewCartId() {
        return String.valueOf(jedisClient.incr(cartIdINCR));
    }
    public VproAuth getLoginInfo(String token) throws CartException {
        System.out.println("this is the redis area");
        logger.info(loginInfoPrefix + token);
        Map<String, String> info = jedisClient.hgetAll(loginInfoPrefix + token);
        logger.info(info.toString());
        if (info == null || info.size() == 0) throw new CartException("user login info could not found in redis!");
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
        logger.info(cartId);
        if (StringUtils.isEmpty(cartId)) throw new CartException("could not get cartId from redis by userId");
        System.out.println(StringUtils.isEmpty(cartId));
        return cartId;
    }

    @Override
    public void setCartIdWithUserId(String userId, String cartId) {
        jedisClient.hset(userCartIdHash, userId, cartId);
    }

    @Override
    public Boolean checkCartExists(String cartId) throws CartException {
        Boolean isExists = jedisClient.exists(cartId);
        if (!isExists) throw new CartException("userCart not exist in redis");
        return isExists;
    }

    @Override
    public Boolean delCartItem(CartDetail cartDetail) throws CartException {
        Long res = jedisClient.zremrangeByScore(
                (cartDetail.getCartIsCookie() ?  cookieCartPrefix : userCartPrefix) + cartDetail.getCartParentId(),
                cartDetail.getCartCourseId().doubleValue(),
                cartDetail.getCartCourseId().doubleValue()
        );
        if (res <= 0) throw new CartException("delete item from user cart failed!" + cartDetail.toString());
        return (res > 0);
    }

    @Override
    public Boolean mergeCart(String cookieCartId, String cartId) throws CartException {
        if (!jedisClient.exists(cookieCartPrefix + cookieCartId)) return true;
        Set<Tuple> items = jedisClient.zrangeWithScores(cookieCartPrefix + cookieCartId, 0L, -1L);
        if (items.size() == 0) return true;
        Long cartItemsCount = jedisClient.zcount(userCartPrefix + cartId, 0d, -1d);
        for(Tuple tuple : items) {
            jedisClient.zadd(userCartPrefix + cartId, tuple.getScore(), tuple.getElement());
        }
        if (jedisClient.zcount(userCartPrefix + cartId, 0d, -1d) != (cartItemsCount + items.size()))
            throw new CartException("mergeCart failed! some of cookieItems tranfer to userCart failed");
        return true;
    }
}
