package com.mark.manager.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class PayServiceAbstract implements PayService{
    // 支付宝支付参数配置 //
    @Value("${ALIPAY.APPID}")
    protected String appId;//应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    @Value("${ALIPAY.PRIVATEKEY}")
    protected String merchantPrivateKey;//商户私钥，您的PKCS8格式RSA2私钥
    @Value("${ALIPAY.ALIPAY_PUBLIC_KEY}")
    protected String alipayPublicKey;//支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。//支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    @Value("${ALIPAY.NOTIFY_URL}")
    protected String notifyUrl;//服务器异步通知页面路径
    @Value("${ALIPAY.RETURN_URL}")
    protected String returnUrl;//页面跳转同步通知页面路径
    @Value("${ALIPAY.SIGN_TYPE}")
    protected String signType = "RSA2";//签名方式
    protected String charset = "utf-8";//字符编码格式
    @Value("${ALIPAY.GATEWAY_URL}")
    protected String gatewayUrl;//支付宝网关
    @Value("${ALIPAY.PID}")
    protected String sellerId; // 支付宝商户id
    @Value("${ordersBelongUserPrefix}")
    protected String orderBelongUserPrefix;

    @Value("${payLockPrefix}")
    protected String payLockPrefix;

/*
    // 微信支付参数配置 //
    @Value("${WXPAY.APPID}")
    protected String APPID;//公众账号ID
    @Value("${WXPAY.MCHID}")
    protected String MCHID;//微信支付商户号
    @Value("${WXPAY.KEY}")
    protected String KEY;//API密钥
    @Value("${WXPAY.APPSECRET}")
    protected String APPSECRET;//AppSecret是APPID对应的接口密码
    @Value("${WXPAY.NOTIFY_URL}")
    protected String NOTIFY_URL;//回调地址。测试回调必须保证外网能访问到此地址
    @Value("${WXPAY.CREATE_IP}")
    protected String CREATE_IP;//发起请求的电脑IP
*/
}
