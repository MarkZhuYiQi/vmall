package com.mark.manager.daoImpl;

import com.github.pagehelper.PageInfo;
import com.mark.common.exception.OrderException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("orderRedis")
public class OrderDaoByRedisImpl extends OrderDaoAbstract {
    @Autowired
    JedisClient jedisClient;

    @Value("${orderIdINCR}")
    String orderIdINCR;
    @Value("${ordersBelongUserPrefix}")
    String ordersBelongUserPrefix;

    @Override
    public String getNewOrderId() {
        return String.valueOf(jedisClient.incr(orderIdINCR));
    }

    /**
     * 名字为 user.order.set.[ORDERPAYMENT][USERID]
     * sortedSet score为页码，member为整页数据
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public Boolean setUserOrderCache(List<Order> orders, OrderCriteria orderCriteria) throws OrderException {
        String orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        Long res = jedisClient.zadd(orderKey, orderCriteria.getpageNum().doubleValue(), BeanUtil.parseObjToJson(orders));
        if (res <= 0) throw new OrderException("userOrder cache set failed!");
        return res > 0;
    }

    @Override
    public List<Order> getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        String orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        Integer offset = (orderCriteria.getpageNum() - 1);
        Set<String> cache = jedisClient.zRange(orderKey, offset.longValue(), offset.longValue());
        if (cache.size() != 1) throw new OrderException("get orders cache failed!");
        List<String> jsonOrder = new ArrayList<String>(cache);
        List<Order> orders = BeanUtil.parseObjToList(jsonOrder.get(0), Order.class);
        return orders;
    }
}