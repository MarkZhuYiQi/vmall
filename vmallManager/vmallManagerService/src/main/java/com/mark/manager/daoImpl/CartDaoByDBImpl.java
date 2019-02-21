package com.mark.manager.daoImpl;

import com.mark.common.exception.CartException;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.mapper.CartMapper;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCart;
import com.mark.manager.pojo.VproCartExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cartRedis")
public class CartDaoByDBImpl extends CartDaoAbstract {
    @Autowired
    VproCartMapper vproCartMapper;
    @Autowired
    CartMapper cartMapper;

    @Override
    public void createUserCart(String cartId, VproAuth auth) {
        VproCart vproCart = new VproCart();
        vproCart.setCartId(Long.parseLong(cartId));
        vproCart.setCartUserid(auth.getAuthId());
        vproCart.setCartStatus("1");
        vproCart.setCartAddtime(String.valueOf(System.currentTimeMillis() / 1000));
        vproCartMapper.insertSelective(vproCart);
    }

    @Override
    public Cart loadUserCart(String cartId) {
        return cartMapper.loadUserCart(cartId);
    }

    @Override
    public Cart addItem() {
        return null;
    }

    @Override
    public String getCartIdByUserId(Integer userId) throws CartException {
        VproCartExample vproCartExample = new VproCartExample();
        vproCartExample.createCriteria().andCartUseridEqualTo(userId);
        List<VproCart> list = vproCartMapper.selectByExample(vproCartExample);
        if (list == null || list.size() == 0) throw new CartException("no cart has been set in DB");
        return String.valueOf(list.get(0).getCartId());
    }
}
