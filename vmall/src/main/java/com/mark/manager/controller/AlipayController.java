package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
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
//        vo.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        vo.setOut_trade_no("20120120120102102");
        vo.setTotal_amount("0.01");
        vo.setTimeout_express("1d");
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

    /**
     * 得到订单号，查询订单数据是否存在，是否和前台传来的信息一致，课程是否可用。
     * 检查完毕，进行支付页面生成操作。
     * @param orderId
     * @return
     */
    @GetMapping("{orderId:\\d+}")
    private Result payForOrder(@PathVariable("orderId") String orderId) {
        JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwtUserDetails.getUserId();
        try {

        }
    }
}
