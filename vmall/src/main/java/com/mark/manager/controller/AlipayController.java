package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.mark.common.constant.OrderConstant;
import com.mark.common.exception.OrderException;
import com.mark.common.exception.PayException;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.common.util.BeanUtil;
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
//        logger.info("{}", vo.toString());
        try {
            String res = payService.testPay(vo);
            logger.info(res);
            return new Result(res);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return new Result(Integer.parseInt(e.getErrCode()), e.getErrMsg());
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
//            vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
            vo.setOut_trade_no(order.getOrderId());
            vo.setProduct_code("FAST_INSTANT_TRADE_PAY");
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
    @PostMapping("async")
    private String alipayAsyncCallback(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        try {
            Map<String, String> params = getParamsMap(httpServletRequest);
            logger.info("AsyncCallback-params: {}", params);
            payService.alipayAsyncCallback(params);
            return "success";
        } catch (AlipayApiException e) {
            logger.error("verify Signature failed!");
            e.printStackTrace();
            return "failed";
        } catch (PayException e) {
            logger.error("{}, {}", e.getMsg(), e.getCode());
            return "failed";
        }
    }
    @GetMapping("sync")
    private String alipaySyncCallback(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        try {
            Map<String, String> params = getParamsMap(httpServletRequest);
            logger.info("SyncCallback-params: {}", params);
            boolean verify = payService.alipayVerifySignature(params);
            if (verify) {
                return "success";
            } else {
                logger.info("verify Signature failed！params: {}", params);
                return "failed";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
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
    @GetMapping("test")
    public void Test () {
        String json = "{\"gmt_create\":\"2019-03-15 15:51:16\",\"charset\":\"utf-8\",\"gmt_payment\":\"2019-03-15 15:51:22\",\"notify_time\":\"2019-03-15 15:51:23\",\"subject\":\"testCharset\",\"sign\":\"PXwZOMn1ZqJEGWvZIvLVphoqeBuB7ZnO5LSxyjkGDmxqu+v6kDnh7YRQIAvTXqvI35WyoXbi9JNzU0uPhtYOiYm7g5PWp5D3kYnLVoG/ohVLTwK+VWsj5+X6cjaR+zR7RuTHbaoOdYkzBGgoNpYnf8KvQeg/MmMljGm8+PPAcrXAEJ0O8evC+zz0osbh4ACECr7ZVS5kW7IU5fIOri3btA64U3WvA47T9EMU5bdihENtf1m54648DeaDKqyqfKPf+YbHvCLd188k8w8HVolHk3jVOhjsGjF/TDaF2SjCtMYK6IeOkuqOUvfklw1yjB4g4o1HJ3E6Og/f3rCQZSA+tg==\",\"buyer_id\":\"2088102173082141\",\"invoice_amount\":\"180.00\",\"version\":\"1.0\",\"notify_id\":\"2471d26c73ddf0cad9787ca7f652fa4h31\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"180.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"6406207152511258624\",\"total_amount\":\"180.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2019031522001482140509827462\",\"auth_app_id\":\"2016082000290082\",\"receipt_amount\":\"180.00\",\"point_amount\":\"0.00\",\"app_id\":\"2016082000290082\",\"buyer_pay_amount\":\"180.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088102172130805\"}";
        Map<String, String> params = BeanUtil.parseJsonToObj(json, Map.class);
        logger.info(params.toString());
        try {
            boolean res = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType);
            System.out.println(res);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
