package com.mark.manager.daoImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.OrderDaoAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("orderRedis")
public class OrderDaoByRedisImpl extends OrderDaoAbstract {
    @Autowired
    JedisClient jedisClient;

    @Value("orderIdINCR")
    String orderIdINCR;

    @Override
    public String getNewOrderId() {
        return String.valueOf(jedisClient.incr(orderIdINCR));
    }
}
