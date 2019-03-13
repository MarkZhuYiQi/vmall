package com.mark.manager.daoImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mark.common.exception.OrderException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.dao.OrderDaoAbstract;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.OrderSub;
import com.mark.manager.mapper.OrderMapper;
import com.mark.manager.mapper.OrderSubMapper;
import com.mark.manager.mapper.VproOrderMapper;
import com.mark.manager.mapper.VproOrderSubMapper;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.pojo.VproOrderExample;
import com.mark.manager.pojo.VproOrderSub;
import com.mark.manager.pojo.VproOrderSubExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("orderDB")
public class OrderDaoByDBImpl extends OrderDaoAbstract {
    @Autowired
    VproOrderMapper vproOrderMapper;
    @Autowired
    VproOrderSubMapper vproOrderSubMapper;
    @Autowired
    OrderSubMapper orderSubMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    JedisClient jedisClient;
    @Value("${orderPrefix}")
    String orderPrefix;
    @Value("${ordersBelongUserPrefix}")
    String ordersBelongUserPrefix;


    @Override
    public Boolean insertOrder(VproOrder vproOrder) throws OrderException {
        Integer res = vproOrderMapper.insertSelective(vproOrder);
        if (res <= 0) throw new OrderException("create order failed!" + vproOrder.toString());
        return res > 0;
    }

    @Override
    public Boolean insertOrderSub(List<OrderSub> subs) throws OrderException {
        Integer res = orderSubMapper.batchInsertSubOrder(subs);
        if (res <= 0) throw new OrderException("insert order sub infomation failed!" + subs.toString());
        return res > 0;
    }

    @Override
    public List<VproOrder> getOrdersByUserId(Integer userId) {
        VproOrderExample vproOrderExample = new VproOrderExample();
        vproOrderExample.createCriteria().andUserIdEqualTo(userId);
        return vproOrderMapper.selectByExample(vproOrderExample);
    }

    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
        vproOrderSubExample.createCriteria().andOrderIdIn(ordersId).andCourseIdIn(coursesId);
        return vproOrderSubMapper.selectByExample(vproOrderSubExample);
    }

    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        if (orderCriteria.getUserId() == null) throw new OrderException("user id does not exist, could not procceed");
        PageHelper.startPage(orderCriteria.getpageNum(), orderCriteria.getPageSize());
        VproOrderExample vproOrderExample = new VproOrderExample();
        VproOrderExample.Criteria criteria = vproOrderExample.createCriteria().andUserIdEqualTo(orderCriteria.getUserId());
        vproOrderExample.setOrderByClause("order_time desc");
        if (orderCriteria.getOrderPayment() != -1) criteria.andOrderPaymentEqualTo(orderCriteria.getOrderPayment());
        List<VproOrder> list = vproOrderMapper.selectByExample(vproOrderExample);
        PageInfo<VproOrder> page = new PageInfo(list);
        List<Order> orders = new ArrayList<>();
        for (VproOrder vproOrder : page.getList()) {
            Order order;
            order = DtoUtil.vproOrder2Order(vproOrder);
            VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
            vproOrderSubExample.createCriteria().andOrderIdEqualTo(vproOrder.getOrderId());
            List<VproOrderSub> vproOrderSub = vproOrderSubMapper.selectByExample(vproOrderSubExample);
            List<OrderSub> subs = new ArrayList<>();
            for (VproOrderSub s : vproOrderSub) {
                subs.add(DtoUtil.vproOrderSub2OrderSub(s));
            }
            order.setOrderSubs(subs);
            orders.add(order);
        }
        OrderResult orderResult = new OrderResult();
        orderResult.setOrders(orders);
        orderResult.setPageNum(page.getPageNum());
        orderResult.setPageSize(page.getPageSize());
        orderResult.setTotal(page.getTotal());

        System.out.println(orderResult);
//        List<Order> orders = orderMapper.getOrderByCriteria(orderCriteria);
        return orderResult;
    }

    @Override
    public Boolean checkCourseIfBought(String courseId, Integer userId) throws OrderException {
        try {
            VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
            vproOrderSubExample.createCriteria().andUserIdEqualTo(userId).andCourseIdEqualTo(Integer.parseInt(courseId));
            List<VproOrderSub> orderSub = vproOrderSubMapper.selectByExample(vproOrderSubExample);
            if (orderSub.size() <= 0) return false;
            VproOrderExample vproOrderExample = new VproOrderExample();
            vproOrderExample.createCriteria().andOrderIdEqualTo(orderSub.get(0).getOrderId());
            List<VproOrder> vproOrders = vproOrderMapper.selectByExample(vproOrderExample);
            if (vproOrders.size() == 0) return false;
            if (vproOrders.get(0).getOrderPayment() == 1) return true;
            return false;
        } catch (Exception e) {
            throw new OrderException("could not check course if is bought." + e.getMessage());
        }
    }
}
