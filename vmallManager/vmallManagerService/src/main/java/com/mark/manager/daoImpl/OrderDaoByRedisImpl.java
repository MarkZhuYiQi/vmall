package com.mark.manager.daoImpl;

import com.mark.common.exception.OrderException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("orderRedis")
public class OrderDaoByRedisImpl extends OrderDaoAbstract {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoByRedisImpl.class);

    @Autowired
    JedisClient jedisClient;

    @Value("${orderIdINCR}")
    String orderIdINCR;

    @Value("${orderPrefix}")
    String orderPrefix;

    @Value("${ordersBelongUserPrefix}")
    String ordersBelongUserPrefix;

    @Value("${ordersUnionBelongUserPrefix}")
    String ordersUnionBelongUserPrefix;

    @Override
    public String getNewOrderId() {
        return String.valueOf(jedisClient.incr(orderIdINCR));
    }

    @Override
    public Long getOrdersCount(OrderCriteria orderCriteria) {
        String key;
        if (orderCriteria.getOrderPayment() == -1) {
            unionOrders(orderCriteria.getUserId());
            key = ordersUnionBelongUserPrefix + String.valueOf(orderCriteria.getUserId());
        } else {
            key = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        }
        return jedisClient.zcount(key, "-inf", "+inf");
    }

    private void unionOrders(Integer userId) {
        jedisClient.zunionstore(
                ordersUnionBelongUserPrefix + String.valueOf(userId),
                ordersBelongUserPrefix + "0" + String.valueOf(userId),
                ordersBelongUserPrefix + "1" + String.valueOf(userId),
                ordersBelongUserPrefix + "2" + String.valueOf(userId),
                ordersBelongUserPrefix + "3" + String.valueOf(userId)
        );
    }
    private void unionClosedOrders(Integer userId) {
        jedisClient.zunionstore(
                ordersUnionBelongUserPrefix + "2" + String.valueOf(userId),
                ordersBelongUserPrefix + "2" + String.valueOf(userId),
                ordersBelongUserPrefix + "3" + String.valueOf(userId)
        );
    }

    /**
     * 名字为 user.order.set.[ORDERPAYMENT][USERID]
     * sortedSet score为orderId，member为orderId
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public Boolean setUserOrderCache(OrderResult orderResult, OrderCriteria orderCriteria) throws OrderException {
        if (orderCriteria.getOrderPayment() == -1) {
            Map<String, Double> scoreMembers0 = new HashMap<>();
            Map<String, Double> scoreMembers1 = new HashMap<>();
            Map<String, Double> scoreMembers2 = new HashMap<>();
            for (Order order : orderResult.getOrders()) {
                String hashKey = orderPrefix + order.getOrderId();
                if (!jedisClient.exists(hashKey)) {
                    Map<String, String> hash = DtoUtil.order2Map(order);
                    jedisClient.hmset(hashKey, hash);
                }
                if (order.getOrderPayment() == 0) scoreMembers0.put(order.getOrderId(), new Double(order.getOrderId()));
                if (order.getOrderPayment() == 1) scoreMembers1.put(order.getOrderId(), new Double(order.getOrderId()));
                if (order.getOrderPayment() == 2) scoreMembers2.put(order.getOrderId(), new Double(order.getOrderId()));
            }
            setUserOrderCacheByOrderPayment(String.valueOf(orderCriteria.getUserId()), "0", scoreMembers0);
            setUserOrderCacheByOrderPayment(String.valueOf(orderCriteria.getUserId()), "1", scoreMembers1);
            setUserOrderCacheByOrderPayment(String.valueOf(orderCriteria.getUserId()), "2", scoreMembers2);
        } else {
            Map<String, Double> scoreMembers = new HashMap<>();
            for (Order order : orderResult.getOrders()) {
                String hashKey = orderPrefix + order.getOrderId();
                if (!jedisClient.exists(hashKey)) {
                    Map<String, String> hash = DtoUtil.order2Map(order);
                    jedisClient.hmset(hashKey, hash);
                }
                scoreMembers.put(order.getOrderId(), new Double(order.getOrderId()));
            }
            setUserOrderCacheByOrderPayment(String.valueOf(orderCriteria.getUserId()), String.valueOf(orderCriteria.getOrderPayment()), scoreMembers);
        }
        return true;
    }
    private void setUserOrderCacheByOrderPayment(String userId, String orderPayment, Map<String, Double> scoreMembers) {
        String orderKey = ordersBelongUserPrefix + orderPayment + userId;
        jedisClient.zadd(orderKey, scoreMembers);
    }
/*
    @Override
    public Boolean setUserOrderCache(OrderResult orderResult, OrderCriteria orderCriteria) throws OrderException {
        String orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        Long res = jedisClient.zadd(orderKey, orderCriteria.getpageNum().doubleValue(), BeanUtil.parseObjToJson(orderResult));
        return true;
    }
*/

    /**
     * 暂时不使用缓存中的订单数据，缓存方案需要重构。
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        String orderKey;
        if (orderCriteria.getOrderPayment() != -1) {
            orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        } else {
            orderKey = ordersUnionBelongUserPrefix + String.valueOf(orderCriteria.getUserId());
        }
        Long total = getOrdersCount(orderCriteria);
        logger.info("order count: {}", total);
        logger.info("current page : {}, size: {}", orderCriteria.getpageNum(), orderCriteria.getPageSize());
        if ((orderCriteria.getPageSize() * (orderCriteria.getpageNum() - 1)) > total.intValue()) {
            throw new OrderException("get order from cache failed! the orders wanna get are more than total");
        }
        // 对应页码的订单起始位置
        Integer start = (orderCriteria.getpageNum() - 1) * orderCriteria.getPageSize();
        // 对应订单结束为止
        Integer offset = (orderCriteria.getpageNum() * orderCriteria.getPageSize() - 1);
        logger.info("key: {}, start int: {}, end int: {}", orderKey, start, offset);
        Set<String> cachedOrdersId = jedisClient.zrevrange(orderKey, start.longValue(), offset.longValue());
        logger.info("cached order id: {}", cachedOrdersId.toString());
        if (cachedOrdersId.size() <= 0) throw new OrderException("get orders cache failed!");
        // 该页订单id
        List<String> ordersId = new ArrayList<>(cachedOrdersId);
        int size = ordersId.size();
        // 生成整页的订单详情
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // 订单对应的hash key
            Map<String, String> hash = jedisClient.hgetAll(orderPrefix + ordersId.get(i));
            orders.add(DtoUtil.map2Order(hash));
        }
        // 配置结果
        OrderResult orderResult = new OrderResult();
        orderResult.setOrders(orders);
        orderResult.setPageNum(orderCriteria.getpageNum());
        orderResult.setPageSize(orderCriteria.getPageSize());
        orderResult.setTotal(total);
        return orderResult;
    }

    @Override
    public void delUserOrderCache(String orderPayment, Integer userId) throws OrderException {
        jedisClient.del(ordersBelongUserPrefix + orderPayment + String.valueOf(userId));
    }

    @Override
    public List<Long> getOrdersIdByCriteria(Integer userId, Integer orderPayment) {
        if (orderPayment == 2) unionClosedOrders(userId);
        String key = ordersBelongUserPrefix + String.valueOf(orderPayment) + String.valueOf(userId);
        Set<String> cachedOrdersId = jedisClient.zrevrange(key, 0L, -1L);
        List<Long> ordersId = new ArrayList<>();
        for (String s : cachedOrdersId) {
            ordersId.add(Long.parseLong(s));
        }
        return ordersId;
    }

    @Override
    public Order getOrderSpecified(Long orderId, Integer userId) throws OrderException {
        String key = orderPrefix + String.valueOf(orderId);
        if (jedisClient.exists(key)) {
            Map<String, String> orderMap = jedisClient.hgetAll(key);
            return DtoUtil.map2Order(orderMap);
        }
        throw new OrderException("order cache does not exist");
    }
}
