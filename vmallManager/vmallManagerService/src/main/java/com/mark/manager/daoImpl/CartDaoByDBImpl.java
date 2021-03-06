package com.mark.manager.daoImpl;

import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.DtoUtil;
import com.mark.manager.mapper.VproCartDetailMapper;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mark.common.exception.CartException;
import com.mark.manager.mapper.CartMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("cartDB")
public class CartDaoByDBImpl extends CartDaoAbstract {
    private static final Logger logger = LoggerFactory.getLogger(CartDaoByDBImpl.class);

    @Autowired
    VproCartMapper vproCartMapper;

    @Autowired
    VproCartDetailMapper vproCartDetailMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public void createUserCart(String cartId, VproAuth auth) throws CartException {
        try {
            VproCart vproCart = new VproCart();
            vproCart.setCartId(Long.parseLong(cartId));
            vproCart.setCartUserid(auth.getAuthId());
            vproCart.setCartStatus("1");
            vproCart.setCartAddtime(String.valueOf(System.currentTimeMillis() / 1000));
            System.out.println(vproCart.toString());
            vproCartMapper.insertSelective(vproCart);
        } catch (Exception e) {
            String err = String.format("create user cart for user %s in DB failed! %s", auth.getAuthAppid(), e.getMessage());
            logger.error(err);
            throw new CartException(err);
        }

    }

    @Override
    public Cart loadUserCart(String cartId, String userId) throws CartException {

//        Cart cart =  cartMapper.loadUserCart(cartId, userId);
        VproCartExample vproCartExample = new VproCartExample();
        vproCartExample.createCriteria().andCartIdEqualTo(Long.parseLong(cartId)).andCartUseridEqualTo(Integer.valueOf(userId));
        List<VproCart> vproCarts = vproCartMapper.selectByExample(vproCartExample);
        if (vproCarts.size() < 1) throw new CartException("user cart does not exist! userId: " + userId);
        VproCart vproCart = vproCarts.get(0);
        VproCartDetailExample vproCartDetailExample = new VproCartDetailExample();
        vproCartDetailExample.createCriteria().andCartParentIdEqualTo(Long.parseLong(cartId));
        List<VproCartDetail> detail = vproCartDetailMapper.selectByExample(vproCartDetailExample);
        Cart cart = DtoUtil.VproCart2Cart(vproCart, detail == null || detail.size() == 0 ? new ArrayList<>() : detail);
        return cart;
    }

    @Override
    public VproCartDetail addItem(CartDetail cartDetail) throws CartException {
        VproCartDetail vproCartDetail = DtoUtil.cartDetail2VproCartDetail(cartDetail);
        VproCartDetailExample vproCartDetailExample = new VproCartDetailExample();
        vproCartDetailExample.createCriteria().andCartCourseIdEqualTo(String.valueOf(cartDetail.getCartCourseId())).andCartParentIdEqualTo(cartDetail.getCartParentId());
        if (vproCartDetailMapper.countByExample(vproCartDetailExample) > 0) throw new CartException("add item to userCart in DB failed! course already exists." + cartDetail.toString());
        Integer count = vproCartDetailMapper.insertSelective(vproCartDetail);
        if (count <= 0) throw new CartException("insert into cartDetail failed");
        return vproCartDetail;
    }

    @Override
    public String getCartIdByUserId(Integer userId) throws CartException {
        VproCartExample vproCartExample = new VproCartExample();
        vproCartExample.createCriteria().andCartUseridEqualTo(userId);
        List<VproCart> list = vproCartMapper.selectByExample(vproCartExample);
        if (list == null || list.size() == 0) throw new CartException("no cart has been set in DB");
        return String.valueOf(list.get(0).getCartId());
    }

    @Override
    public Boolean checkCartExists(String cartId) throws CartException {
        VproCartExample vproCartExample = new VproCartExample();
        vproCartExample.createCriteria().andCartIdEqualTo(Long.valueOf(cartId));
        Boolean isExist = (vproCartMapper.countByExample(vproCartExample) > 0);
        if (!isExist) throw new CartException("userCart not exist in DB");
        return isExist;
    }

    @Override
    public Boolean delCartItem(CartDetail cartDetail) throws CartException {
        VproCartDetailExample vproCartDetailExample = new VproCartDetailExample();
        vproCartDetailExample
                .createCriteria()
                .andCartParentIdEqualTo(cartDetail.getCartParentId())
                .andCartCourseIdEqualTo(String.valueOf(cartDetail.getCartCourseId()))
                .andCartIsCookieEqualTo(cartDetail.getCartIsCookie());
        Integer res = vproCartDetailMapper.deleteByExample(vproCartDetailExample);
        if (res <= 0) throw new CartException("delete item from user cart failed!" + cartDetail.toString());
        return (res > 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mergeCart(String cookieCartId, String cartId) throws CartException {
        try {
            // 查看是否有重复的商品
            List<String> repeats = cartMapper.compareCartItemsWithCookieCartAndUserCart(cartId, cookieCartId);
            if (repeats.size() > 0) {
                VproCartDetailExample vproCartDetailExample = new VproCartDetailExample();
                vproCartDetailExample.createCriteria().andCartCourseIdIn(repeats);
                vproCartDetailMapper.deleteByExample(vproCartDetailExample);
            } else {
                return true;
            }
            // 将属于该用户的商品更新到他的购物车下。
            VproCartDetailExample v1 = new VproCartDetailExample();
            v1.createCriteria().andCartParentIdEqualTo(Long.parseLong(cookieCartId));
            VproCartDetail vproCartDetail = new VproCartDetail();
            vproCartDetail.setCartParentId(Long.parseLong(cartId));
            Integer res = vproCartDetailMapper.updateByExampleSelective(vproCartDetail, v1);
            return (res > 0);
        } catch (Exception e) {
            throw new CartException("update repeat items error!"+e.getMessage());
        }

    }
}
