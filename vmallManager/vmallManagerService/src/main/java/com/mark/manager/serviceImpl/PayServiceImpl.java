package com.mark.manager.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mark.common.util.BeanUtil;
import com.mark.manager.service.PayServiceAbstract;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl extends PayServiceAbstract {
    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
    @Override
    public String alipayPay(AlipayVo alipayVo) throws AlipayApiException {
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
    public void alipayAsyncCallback() {

    }

    @Override
    public void alipaySyncCallback() {

    }
}
