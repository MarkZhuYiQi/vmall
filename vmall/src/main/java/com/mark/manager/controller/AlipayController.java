package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.mark.common.pojo.JwtUserDetails;
import com.mark.manager.bo.Result;
import com.mark.manager.service.OrderService;
import com.mark.manager.service.PayService;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        }
    }
}
