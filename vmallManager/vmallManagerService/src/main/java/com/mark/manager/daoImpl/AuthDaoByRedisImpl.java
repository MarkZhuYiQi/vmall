package com.mark.manager.daoImpl;

import com.mark.common.exception.AuthException;
import com.mark.common.exception.CartException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.AuthDao;
import com.mark.manager.pojo.VproAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("authRedis")
public class AuthDaoByRedisImpl implements AuthDao {

    @Autowired
    private JedisClient jedisClient;

    // 用户登录信息存放前缀，后加token，构成键名
    @Value("${loginInfoPrefix}")
    String loginInfoPrefix;

    @Override
    public VproAuth getLoginInfo(String token) throws AuthException {
        Map<String, String> info = jedisClient.hgetAll(loginInfoPrefix + token);
        if (info == null || info.size() == 0) throw new AuthException("user login info could not found in redis!");
        return  BeanUtil.mapToBean(info, VproAuth.class);
    }
}
