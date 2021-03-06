package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.constant.CartConstant;
import com.mark.common.exception.AuthException;
import com.mark.common.exception.CartException;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Cart;
import com.mark.manager.dto.CartDetail;
import com.mark.manager.dto.Courses;
import com.mark.manager.service.AuthService;
import com.mark.manager.service.CartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "cart")
public class CartController {
    @Reference()
    private CartService cartService;

    @Reference()
    private AuthService authService;

    @GetMapping("")
    public Result loadUserCart(HttpServletRequest httpServletRequest) {
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
    public Result delItemInCart(@RequestBody CartDetail cartDetail, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("MyToken");
        try {
            cartService.delItemFromCart(cartDetail, token);
            return new Result(true);
        } catch (CartException e) {
            return new Result(e.getCode(), e.getMsg());
        } catch (AuthException e) {
            return new Result(e.getCode(), e.getMsg());
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
    @PostMapping("detail")
    public Result getCartItemDetail(@RequestBody List<String> list) {
        if (list == null || list.size() == 0) return new Result(CartConstant.CART_ITEM_NULL, "No Items found in cart!");
        try {
            List<Courses> courses = cartService.getCourseDetailInCart(list);
            return new Result(courses);
        } catch (CartException ce) {
            return new Result(ce.getCode(), ce.getMsg());
        }
    }
}
