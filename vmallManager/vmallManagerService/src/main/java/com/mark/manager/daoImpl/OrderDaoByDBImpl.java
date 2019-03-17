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
    public Long getOrdersCount(OrderCriteria orderCriteria) {
        VproOrderExample vproOrderExample = new VproOrderExample();
        Integer orderPaymentId;
        if (orderCriteria.getOrderPayment() != -1) {
            orderPaymentId = orderCriteria.getOrderPayment();
            vproOrderExample.createCriteria().andOrderPaymentEqualTo(orderPaymentId);
        }
        return vproOrderMapper.countByExample(vproOrderExample);
    }

    @Override
    public List<VproOrderSub> getExistCourseByUserOrder(List<Long> ordersId, List<Integer> coursesId) {
        VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
        vproOrderSubExample.createCriteria().andOrderIdIn(ordersId).andCourseIdIn(coursesId);
        return vproOrderSubMapper.selectByExample(vproOrderSubExample);
    }

    /**
     * 一口气取出所有该payment分类下的订单
     * 0，未支付；1：支付成功；2：支付失败/超时(3)
     * @param orderCriteria
     * @return
     * @throws OrderException
     */
    @Override
    public OrderResult getOrdersByCriteria(OrderCriteria orderCriteria) throws OrderException {
        if (orderCriteria.getUserId() == null) throw new OrderException("user id does not exist, could not procceed");
        // 分页范围
//        PageHelper.startPage(orderCriteria.getpageNum(), orderCriteria.getPageSize());
        VproOrderExample vproOrderExample = new VproOrderExample();
        VproOrderExample.Criteria criteria = vproOrderExample.createCriteria().andUserIdEqualTo(orderCriteria.getUserId());
        vproOrderExample.setOrderByClause("order_time desc");
        if (orderCriteria.getOrderPayment() != -1) {
            List<Integer> payments = new ArrayList<>();
            payments.add(2);
            payments.add(3);
            if (orderCriteria.getOrderPayment() == 2) {
                criteria.andOrderPaymentIn(payments);
            } else {
                criteria.andOrderPaymentEqualTo(orderCriteria.getOrderPayment());
            }
        }
        List<VproOrder> list = vproOrderMapper.selectByExample(vproOrderExample);
        // 获得分页内容
//        PageInfo<VproOrder> page = new PageInfo(list);
        List<Order> orders = new ArrayList<>();
        // 转换结果， 得到List<Order>
//        for (VproOrder vproOrder : page.getList()) {
        for (VproOrder vproOrder : list) {
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
        // 设置订单显示
        orderResult.setOrders(orders);
        // 当前订单页码
        orderResult.setPageNum(orderCriteria.getpageNum());
        // 订单页面容量
        orderResult.setPageSize(orderCriteria.getPageSize());
        // 订单总数
        orderResult.setTotal(getOrdersCount(orderCriteria));

//        System.out.println(orderResult);
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

    @Override
    public Order getOrderSpecified(Long orderId, Integer userId) throws OrderException {
        VproOrderExample  vproOrderExample = new VproOrderExample();
        vproOrderExample.createCriteria().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId);
        List<VproOrder> list = vproOrderMapper.selectByExample(vproOrderExample);
        if (list == null || list.size() <= 0) throw new OrderException("order could not be found!");
        VproOrderSubExample vproOrderSubExample = new VproOrderSubExample();
        vproOrderSubExample.createCriteria().andOrderIdEqualTo(orderId);
        List<VproOrderSub> subs = vproOrderSubMapper.selectByExample(vproOrderSubExample);
        Order order = DtoUtil.vproOrder2Order(list.get(0));
        if (subs == null || subs.size() == 0) throw new OrderException("order sub items could not be found!");
        List<OrderSub> orderSubs = new ArrayList<>();
        for (VproOrderSub sub : subs) {
            orderSubs.add(DtoUtil.vproOrderSub2OrderSub(sub));
        }
        order.setOrderSubs(orderSubs);
        return order;
    }

    @Override
    public Boolean setOrderExpired(Long orderId, Integer userId) throws OrderException {
        VproOrderExample vproOrderExample = new VproOrderExample();
        vproOrderExample.createCriteria().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId);
        VproOrder vproOrder = new VproOrder();
        vproOrder.setOrderPayment(2);
        Integer res = vproOrderMapper.updateByExampleSelective(vproOrder, vproOrderExample);
        if (res <= 0) throw new OrderException("set order expired failed!");
        return res > 0;
    }

    @Override
    public VproOrder getOrderByOrderId(Long orderId) {
        return vproOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public VproOrder updateOrderStatus(VproOrder vproOrder) {
        vproOrderMapper.updateByPrimaryKeySelective(vproOrder);
        return vproOrderMapper.selectByPrimaryKey(vproOrder.getOrderId());
    }
}
