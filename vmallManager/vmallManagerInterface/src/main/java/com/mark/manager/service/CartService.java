package com.mark.manager.service;

import com.mark.common.exception.CartException;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;

public interface CartService {
    Cart loadUserCartWithLogin(String token) throws CartException;
    Cart loadUserCartWithoutLogin();
    Boolean addItemToCart(CartDetail cartDetail, String token) throws CartException;
    Boolean addItemToCookieCart(CartDetail cartDetail) throws CartException;
    Boolean delItemFromCart(CartDetail cartDetail) throws CartException;
    Boolean mergeCart(String cookieCartId, String token) throws CartException;
}
