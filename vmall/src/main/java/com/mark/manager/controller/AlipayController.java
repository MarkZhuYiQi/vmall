package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.mark.manager.bo.Result;
import com.mark.manager.service.PayService;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "pay")
public class AlipayController {
    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Reference()
    PayService payService;

    @PostMapping("")
    private Result alipayPay() {
        // 测试用
        AlipayVo vo = new AlipayVo();
        vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        vo.setTotal_amount("0.01");
        vo.setSubject("testProduct");
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY");    // 固定的
        logger.info("{}", vo.toString());
        try {
            String res = payService.alipayPay(vo);
            logger.info(res);
            return new Result(res);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return new Result(Integer.parseInt(e.getErrCode()), e.getErrMsg());
        }
    }
}
