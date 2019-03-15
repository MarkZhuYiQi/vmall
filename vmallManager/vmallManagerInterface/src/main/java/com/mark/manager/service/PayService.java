package com.mark.manager.service;

import com.alipay.api.AlipayApiException;
import com.mark.common.exception.PayException;
import com.mark.manager.pojo.VproOrder;
import com.mark.manager.vo.AlipayVo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PayService {
    String testPay(AlipayVo alipayVo) throws AlipayApiException;
    String alipayPay(AlipayVo alipayVo) throws AlipayApiException, PayException;
    VproOrder alipayAsyncCallback(HttpServletRequest httpServletRequest, Map<String, String> params) throws AlipayApiException, UnsupportedEncodingException, PayException;
    void alipaySyncCallback();
    Boolean alipayVerifySignature(HttpServletRequest httpServletRequest, Map<String, String> params) throws AlipayApiException, UnsupportedEncodingException;
}
