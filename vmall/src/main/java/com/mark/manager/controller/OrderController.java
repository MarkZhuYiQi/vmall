package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.exception.OrderException;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.manager.bo.OrderResult;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Order;
import com.mark.manager.dto.OrderCriteria;
import com.mark.manager.dto.PutOrder;
import com.mark.manager.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "order")
public class OrderController {
    @Reference()
    OrderService orderService;

    @PostMapping("")
    public Result putOrder(@RequestBody PutOrder putOrder) {
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = userDetails.getUserId().intValue();
        try {
            Order order = orderService.checkOrder(putOrder, userId);
            order = orderService.putOrder(order, userId);
            return new Result(order);
        } catch (OrderException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }

    /**
     * 分页order缓存思路：
     * 使用到的redis命令：ZUNIONSTORE(destination, numkeys, key, [key...])组合三种订单状态，
     *                  ZREVRANGEBYSCORE(key,max,min,[WITHSCORES])按照分数从高到低排序，按照索引取出数据
     *                  hash表存储每个order的信息
     * 取订单页面的时候，根据页码，获得索引区间，拿到score（orderId），再hmget所有order信息。
     * 最后组成结果返回给前台。
     * @param orderCriteria
     * @return
     */
    @PostMapping("get")
    public Result getOrders(@RequestBody OrderCriteria orderCriteria) {
        try {
            OrderResult orderResult = orderService.getOrdersByCriteria(orderCriteria);
            return new Result(orderResult);
        } catch (OrderException oe) {
            return new Result(oe.getCode(), oe.getMsg());
        }
    }
    @GetMapping("check/{courseId:\\d+}")
    public Result checkCourseIfBought(@PathVariable("courseId") String courseId) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        JwtUserDetails details = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = details.getUserId().intValue();
        try {
            Boolean res = orderService.checkCourseIfBought(courseId, userId);
            return new Result(res);
        } catch (OrderException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }

}
