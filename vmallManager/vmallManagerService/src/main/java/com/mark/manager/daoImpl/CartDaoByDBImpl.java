package com.mark.manager.daoImpl;

import com.mark.manager.dao.CartDao;
import com.mark.manager.dao.CartDaoAbstract;
import com.mark.manager.dto.Cart;
import com.mark.manager.mapper.VproCartMapper;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.pojo.VproCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cartRedis")
public class CartDaoByDBImpl extends CartDaoAbstract {
    @Autowired
    VproCartMapper vproCartMapper;

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
    public Cart loadUserCart(String cartId, VproAuth auth) {

    }

    @Override
    public Cart addItem() {
        return null;
    }
}
