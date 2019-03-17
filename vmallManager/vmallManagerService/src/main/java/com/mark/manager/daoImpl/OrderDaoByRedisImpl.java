package com.mark.manager.daoImpl;

import com.mark.common.exception.OrderException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("orderRedis")
public class OrderDaoByRedisImpl extends OrderDaoAbstract {
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
        String orderPaymentId;
        if (orderCriteria.getOrderPayment() == -1) {
            unionOrders(orderCriteria.getUserId());
            orderPaymentId = "-1";
        }
        orderPaymentId = String.valueOf(orderCriteria.getOrderPayment());
        String key = ordersBelongUserPrefix + orderPaymentId + String.valueOf(orderCriteria.getUserId());
        return jedisClient.zcount(key, "-inf", "+inf");
    }

    private void unionOrders(Integer userId) {
        jedisClient.zunionstore(
                ordersUnionBelongUserPrefix + String.valueOf(userId),
                ordersBelongUserPrefix + "0" + String.valueOf(userId),
                ordersBelongUserPrefix + "1" + String.valueOf(userId),
                ordersBelongUserPrefix + "2" + String.valueOf(userId)
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
        String orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        Map<String, Double> scoreMembers = new HashMap<>();
        for (Order order : orderResult.getOrders()) {
            Map<String, String> hash = new HashMap<String, String>();
            scoreMembers.put(order.getOrderId(), new Double(order.getOrderId()));
            hash.put("orderId", order.getOrderId());
            hash.put("orderPaymentId", order.getOrderPaymentId());
            hash.put("orderPayment", String.valueOf(order.getOrderPayment()));
            hash.put("orderDiscount", order.getOrderDiscount());
            hash.put("orderCouponUsed", order.getOrderCouponUsed() ? "1" : "0");
            hash.put("orderPaymentPrice", order.getOrderPaymentPrice());
            hash.put("orderPrice", order.getOrderPrice());
            hash.put("orderSubs", BeanUtil.parseObjToJson(order.getOrderSubs()));
            hash.put("orderTime", order.getOrderTime());
            hash.put("userId", String.valueOf(order.getUserId()));
            hash.put("orderTitle", order.getOrderTitle());
            jedisClient.hmset(orderPrefix + order.getOrderId(), hash);
        }
        jedisClient.zadd(orderKey, scoreMembers);
        return true;
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
        throw new OrderException("order cache temporary out of service.");
        /*String orderKey = ordersBelongUserPrefix + String.valueOf(orderCriteria.getOrderPayment()) + String.valueOf(orderCriteria.getUserId());
        Integer offset = (orderCriteria.getpageNum() - 1);
        Set<String> cache = jedisClient.zRange(orderKey, offset.longValue(), offset.longValue());
        if (cache.size() != 1) throw new OrderException("get orders cache failed!");
        List<String> jsonOrder = new ArrayList<String>(cache);
        OrderResult orderResult = BeanUtil.parseJsonToObj(jsonOrder.get(0), OrderResult.class);
        return orderResult;*/
    }

    @Override
    public void delUserOrderCache(String orderPayment, Integer userId) throws OrderException {
        jedisClient.del(ordersBelongUserPrefix + orderPayment + String.valueOf(userId));
    }
}
