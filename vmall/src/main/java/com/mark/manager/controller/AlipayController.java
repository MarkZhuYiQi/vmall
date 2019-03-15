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
public class AlipayController {
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
//        vo.setOut_trade_no("20120120120102102");
        vo.setTotal_amount("0.01");
        vo.setTimeout_express("1d");
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
//            vo.setOut_trade_no(order.getOrderId());
            vo.setOut_trade_no("20120120120102102");
            vo.setProduct_code("FAST_INSTANT_TRADE_PAY");
//            vo.setSubject(order.getOrderTitle());
            vo.setSubject("testProduct");
//            vo.setTotal_amount(order.getOrderPrice());
            vo.setTotal_amount("0.01");
            vo.setTimeout_express("1d");
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
    private String alipayAsyncCallback(@RequestBody Map<String, String> maps) {
        logger.error(maps.toString());
        return "failed";
    }
    @GetMapping("sync")
    private String alipaySyncCallback(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        logger.error(params.toString());
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
}
