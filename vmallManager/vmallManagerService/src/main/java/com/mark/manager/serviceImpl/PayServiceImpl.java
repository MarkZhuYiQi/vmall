package com.mark.manager.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mark.common.constant.PayConstant;
import com.mark.common.exception.PayException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.common.util.RedisLockUtil;
import com.mark.manager.service.PayServiceAbstract;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl extends PayServiceAbstract {
    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    JedisClient jedisClient;
    @Value("${payLockPrefix}")
    String payLockPrefix;

    @Override
    public String testPay(AlipayVo alipayVo) throws AlipayApiException {
        logger.info("gatewayUrl: {}", gatewayUrl);
        logger.info("appId: {}", appId);
        logger.info("privateKey: {}", merchantPrivateKey);
        logger.info("alipayPublicKey: {}", alipayPublicKey);
        logger.info("returnUrl: {}", returnUrl);
        logger.info("notifyUrl: {}", notifyUrl);
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, merchantPrivateKey, "json", charset, alipayPublicKey, signType);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setReturnUrl(returnUrl);
        alipayTradePagePayRequest.setNotifyUrl(notifyUrl);
        alipayTradePagePayRequest.setBizContent(BeanUtil.parseObjToJson(alipayVo));
        String result = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        logger.info("result: {}", result);
        return result;
    }

    @Override
    public String alipayPay(AlipayVo alipayVo) throws AlipayApiException, PayException {
        RedisLockUtil redisLock = new RedisLockUtil(jedisClient.getJedisPool().getResource());
        // 默认锁半个小时
        boolean lockStatus = redisLock.lock(payLockPrefix + alipayVo.getOut_trade_no(), JedisUtil.payLockExpiredTimeStamp());
        if (!lockStatus) throw new PayException("someone is paying this order, please check it out.", PayConstant.PAY_TOGETHER_FORBIDDEN);
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, merchantPrivateKey, "json", charset, alipayPublicKey, signType);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setReturnUrl(returnUrl);
        alipayTradePagePayRequest.setNotifyUrl(notifyUrl);
        alipayTradePagePayRequest.setBizContent(BeanUtil.parseObjToJson(alipayVo));
        String result = alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
        logger.info("result: {}", result);
        return result;
    }

    @Override
    public void alipayAsyncCallback() {

    }

    @Override
    public void alipaySyncCallback() {

    }
}
