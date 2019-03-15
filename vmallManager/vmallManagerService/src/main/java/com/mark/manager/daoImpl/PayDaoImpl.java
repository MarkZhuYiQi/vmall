package com.mark.manager.daoImpl;

import com.mark.common.constant.PayConstant;
import com.mark.common.exception.PayException;
import com.mark.manager.dao.OrderDao;
import com.mark.manager.dao.PayDaoAbstract;
import com.mark.manager.pojo.VproOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component("payDao")
public class PayDaoImpl extends PayDaoAbstract {
    private Logger logger = LoggerFactory.getLogger(PayDaoImpl.class);
    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    public VproOrder updateOrderPayStatus(Map<String, String> params) throws PayException {
        //商户订单号
        String outTradeNo = params.get("out_trade_no");
        // 支付宝交易号
        String tradeNo = params.get("trade_no");
        //付款金额
        String totalAmount = params.get("total_amount");

        VproOrder update = new VproOrder();

        VproOrder vproOrder = orderDao.getOrderByOrderId(Long.parseLong(outTradeNo));

        if (vproOrder == null) {
            logger.error("order could not be found! orderId: {}", outTradeNo);
            throw new PayException("order could not be found! ", PayConstant.ORDER_NOT_FOUND);
        }
        update.setOrderPaymentId(tradeNo);
        update.setOrderPaymentPrice(new BigDecimal(totalAmount));
        if (!vproOrder.getOrderPrice().equals(new BigDecimal(totalAmount))) {
            update.setOrderId(vproOrder.getOrderId());
            update.setOrderPayment(3);
            logger.error("payment price mismatch with order! total amount in bill list: {}", totalAmount);
            throw new PayException("payment price mismatch with order! ", PayConstant.ORDER_PRICE_MISMATCH_WITH_PAY);
        }
        update.setOrderId(vproOrder.getOrderId());
        update.setOrderPayment(1);
        return orderDao.updateOrderStatus(update);
    }
}
