package com.mark.manager.service;

import com.mark.common.exception.AuthException;
import com.mark.common.exception.CartException;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.Courses;
import com.mark.manager.pojo.VproCartDetail;

import java.util.List;

public interface CartService {
    Cart loadUserCartWithLogin(String token) throws CartException;
    Boolean addItemToCart(CartDetail cartDetail, String token) throws CartException;
    Boolean addItemToCookieCart(CartDetail cartDetail) throws CartException;
    Boolean delItemFromCart(CartDetail cartDetail, String token) throws CartException, AuthException;
    Boolean mergeCart(String cookieCartId, String token) throws CartException;
    List<Courses> getCourseDetailInCart(List<String> list) throws CartException;
}
