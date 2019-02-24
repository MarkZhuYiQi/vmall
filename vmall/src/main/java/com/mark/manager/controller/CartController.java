package com.mark.manager.controller;

import com.mark.common.exception.CartException;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.CartNo;
import com.mark.manager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("")
    public Result loadUserCart(@RequestBody CartNo cartNo, HttpServletRequest httpServletRequest) {
        // 根据这个token去取登陆信息
        String token = httpServletRequest.getHeader("MyToken");
        try {
            Cart cart = cartService.loadUserCartWithLogin(token);
            return new Result(cart);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @PostMapping("/add")
    public Result addItemToUserCart(@RequestBody @Validated CartDetail cartDetail, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("MyToken");
        try {
            Boolean reply = cartService.addItemToCart(cartDetail, token);
            return new Result(reply);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @PostMapping("/cookie/add")
    public Result addItemToCookieCart(@RequestBody @Validated CartDetail cartDetail) {
        try {
            Boolean reply = cartService.addItemToCookieCart(cartDetail);
            return new Result(reply);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @DeleteMapping("")
    public Result delItemInCart(@RequestBody CartDetail cartDetail) {
        try {
            cartService.delItemFromCart(cartDetail);
            return new Result(true);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMessage());
        }
    }
    @GetMapping("merge/{cookieCartId:\\d+}")
    public Result mergeCart(@RequestParam("cookieCartId") String cookieCartId, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("MyToken");
        try {
            cartService.mergeCart(cookieCartId, token);
            return new Result(true);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
}
