package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.mark.common.constant.OrderConstant;
import com.mark.common.exception.OrderException;
import com.mark.common.exception.PayException;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.common.util.JedisUtil;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Order;
import com.mark.manager.service.OrderService;
import com.mark.manager.service.PayService;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "pay")
public class AlipayController extends PayBaseController{
    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Reference()
    PayService payService;

    @Reference()
    OrderService orderService;

    @PostMapping("")
    private Result alipayPay() {
        // 测试用
        AlipayVo vo = new AlipayVo();
        vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
//        vo.setOut_trade_no("778899445566122323");
        vo.setTotal_amount("0.01");
        vo.setTimeout_express("30m");
        vo.setSubject("testProduct");
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY");    // 固定的
        System.out.println(vo.toString());
        try {
            String res = payService.alipayPay(vo);
            System.out.println(res);
            return new Result(res);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return new Result(Integer.parseInt(e.getErrCode()), e.getErrMsg());
        } catch (PayException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }

    /**
     * 得到订单号，查询订单数据是否存在，是否和前台传来的信息一致，课程是否可用。
     * 检查完毕，进行支付页面生成操作。
     * @param orderId
     * @return
     */
    @GetMapping("{orderId:\\d+}")
    private Result payForOrder(@PathVariable("orderId") Long orderId) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwtUserDetails.getUserId();
        try {
            Order order = orderService.getOrderSpecified(orderId, userId.intValue());
            Long timeStamp = (Long.parseLong(order.getOrderTime()) + (24L * 60L * 60L));
            if (JedisUtil.isExpired(timeStamp.doubleValue())) {
                orderService.setOrderExpired(orderId, userId.intValue());
                return new Result(OrderConstant.ORDER_EXPIRED, "this order has been expired, please put order once again.");
            }
            AlipayVo vo = new AlipayVo();
            vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
//            vo.setOut_trade_no(order.getOrderId());
            vo.setProduct_code("FAST_INSTANT_TRADE_PAY");
            vo.setSubject(order.getOrderTitle());
            vo.setTotal_amount(order.getOrderPrice());
            vo.setTimeout_express("12h");
            logger.info(vo.toString());
            String res = payService.alipayPay(vo);
            return new Result(res);
        } catch (OrderException eo) {
            return new Result(eo.getCode(), eo.getMsg());
        } catch (AlipayApiException ea) {
            return new Result(Integer.parseInt(ea.getErrCode()), ea.getErrMsg());
        } catch (PayException ep) {
            return new Result(ep.getCode(), ep.getMsg());
        }
    }
    @PostMapping("async")
    private String alipayAsyncCallback(HttpServletRequest httpServletRequest) {
        Map<String, String> params = getParamsMap(httpServletRequest);
        /**
         * {gmt_create=2019-03-14 23:42:33,
         * charset=utf-8,
         * gmt_payment=2019-03-14 23:42:38,
         * notify_time=2019-03-14 23:42:40,
         * subject=PowerPoint2010??????????PowerPoint2010??????????,
         * sign=r6+P5KsYrT1v0r+WmNJLAQClE8GSbr8zU6K3LvWa67iWwCy+MxUYsuYEeOCvjTfp2wzJy075iSdaz+8lXXYmp4QFOufNuKYf/h9bgB3GYVReu8TaGO5IeEVdUs/gNJ0nUh/aOq3I0aZeM/twKDt07tHj4cWmlTA1Y7o/ZlofcuOSo55bDgqadpcmyQmk9No0505DGFCB1hIO6FufmNdcS2udtylfGhuNGpopGjd/FCMoZWzRJny1ftO0BS43JjNHOPO02vyKs1lKOVuYkJzxpQ1A5botdCZTW55LSbWFxYtIjiKtlGDgIgQp3E/uRgBGn9jmySZYcbYBixJquAtaYA==,
         * buyer_id=2088102173082141,
         * invoice_amount=69.00,
         * version=1.0,
         * notify_id=75c5d5b7a81b05fbf56a791d05d9886h31,
         * fund_bill_list=[{"amount":"69.00","fundChannel":"ALIPAYACCOUNT"}],
         * notify_type=trade_status_sync,
         * out_trade_no=7b518fce95d74b778b8a4f6d19a9b6bf,
         * total_amount=69.00,
         * trade_status=TRADE_SUCCESS,
         * trade_no=2019031422001482140509827103,
         * auth_app_id=2016082000290082,
         * receipt_amount=69.00,
         * point_amount=0.00,
         * app_id=2016082000290082,
         * buyer_pay_amount=69.00,
         * sign_type=RSA2,
         * seller_id=2088102172130805}
         */
        logger.error(params.toString());
        System.out.println();
        return "failed";
    }
    @GetMapping("sync")
    private String alipaySyncCallback(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        Map<String, String> params = getParamsMap(httpServletRequest);

        /**
         * {charset=utf-8,
         * out_trade_no=6406181955229978624,
         * method=alipay.trade.page.pay.return,
         * total_amount=69.00,
         * sign=2M8iQ4paupoRQIEJKDjLhEfsLaKHts9pRtxqp/dEpZyzzeTZqRLT65u++A7XhgB41vqHw3CjD8qngIj3JD+r1K5PN9I1YdxW1jXy+FLMl1GJQ6kwjCHgwfMXjiAwRly9qZfgJQdHWu2uDVTA3zNQDOzP9KHaLBAz2CEs2qnOyUTqcXSahurHx4vcV8s9ht4nCZutkSyH3NS1Xgqj5ZScmA0EP/3z0O7jj88gpcKXrz5gds7g9inwp68FXP2j1+/lC/1qYtRTeShK3Z97TKrp46gCR9YsC38Wwq3nQPmtwujhV6dZVkyJDeYYyfsvs5LDcbgIX1pnuxhg8fOmu6i6vQ==,
         * trade_no=2019031422001482140509827102,
         * auth_app_id=2016082000290082,
         * version=1.0,
         * app_id=2016082000290082,
         * sign_type=RSA2,
         * seller_id=2088102172130805,
         * timestamp=2019-03-14 23:02:29}
         */
        System.out.println(params);
        return "";
//        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
//
//        //——请在这里编写您的程序（以下代码仅作参考）——
//        if(signVerified) {
//            //商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//
//            //付款金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
//
//            out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
//        }else {
//            out.println("验签失败");
//        }
    }
    private Map<String, String> getParamsMap(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
