package com.mark.manager.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mark.common.constant.PayConstant;
import com.mark.common.exception.PayException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.BeanUtil;
import com.mark.common.util.JedisUtil;
import com.mark.common.util.RedisLockUtil;
import com.mark.manager.dao.PayDao;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.service.PayServiceAbstract;
import com.mark.manager.vo.AlipayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class PayServiceImpl extends PayServiceAbstract {
    private Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    JedisClient jedisClient;

    @Autowired
    @Qualifier("payDao")
    PayDao payDao;

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
    @Override
    public VproOrder alipayAsyncCallback(Map<String, String> params) throws AlipayApiException, UnsupportedEncodingException, PayException {
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(alipayVerifySignature(params)) {
            // 支付宝appid
            String appId = new String(params.get("app_id").getBytes("ISO-8859-1"),"UTF-8");
            if(!appId.equals(this.appId)) {
                logger.error("支付宝appId错误，商户错误！appId: {}", appId);
                throw new PayException("支付宝appId错误，商户错误！");
            }
            String sellerId = new String(params.get("seller_id").getBytes("ISO-8859-1"),"UTF-8");
            if (!sellerId.equals(this.sellerId)) {
                logger.error("支付宝sellerId错误！sellerId: {}", sellerId);
                throw new PayException("支付宝sellerId错误！sellerId: {}");
            }
            try {
                VproOrder vproOrder = payDao.updateOrderPayStatus(params);
                return vproOrder;
            } catch (PayException e) {
                logger.error(e.getMsg());
                throw new PayException(e.getMsg(), e.getCode());
            }
        } else {
            logger.error("验签失败！params: {}" + params);
            throw new PayException("支付宝验签失败！");
        }
    }

    @Override
    public void alipaySyncCallback() {
    }

    @Override
    public Boolean alipayVerifySignature(Map<String, String> params) throws AlipayApiException{
        Jedis jedis = jedisClient.getJedisPool().getResource();
        RedisLockUtil redisLockUtil = new RedisLockUtil(jedis);
        redisLockUtil.unlock(payLockPrefix + params.get("out_trade_no"));
        return AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset, signType); //调用SDK验证签名
    }
}
