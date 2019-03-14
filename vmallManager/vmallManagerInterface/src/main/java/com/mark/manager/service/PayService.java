package com.mark.manager.service;

import com.alipay.api.AlipayApiException;
import com.mark.common.exception.PayException;
import com.mark.manager.vo.AlipayVo;

public interface PayService {
    String testPay(AlipayVo alipayVo) throws AlipayApiException;
    String alipayPay(AlipayVo alipayVo) throws AlipayApiException, PayException;
    void alipayAsyncCallback();
    void alipaySyncCallback();
}
