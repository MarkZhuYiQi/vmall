package com.mark.manager.controller;

import com.mark.common.pojo.JwtUserDetails;
import com.mark.manager.dto.CartNo;
import com.mark.manager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("")
    public void loadUserCart(@RequestBody CartNo cartNo, HttpServletRequest httpServletRequest) {
        // 根据这个token去取登陆信息
        String token = httpServletRequest.getHeader("MyToken");
        if (StringUtils.isEmpty(token)) {

        } else {
            cartService.loadUserCartWithLogin(token);

        }
    }
}
