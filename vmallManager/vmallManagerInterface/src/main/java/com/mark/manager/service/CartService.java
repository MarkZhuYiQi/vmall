package com.mark.manager.service;

import com.mark.manager.dto.Cart;

public interface CartService {
    Cart loadUserCartWithLogin(String token);
    Cart loadUserCartWithoutLogin();

}
