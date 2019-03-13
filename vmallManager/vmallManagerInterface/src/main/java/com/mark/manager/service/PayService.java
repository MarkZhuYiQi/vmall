package com.mark.manager.service;

import com.alipay.api.AlipayApiException;
import com.mark.manager.vo.AlipayVo;

public interface PayService {
    String alipayPay(AlipayVo alipayVo) throws AlipayApiException;
    void alipayAsyncCallback();
    void alipaySyncCallback();
}
