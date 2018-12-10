package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    ShoppingService shoppingService;

    @GetMapping("test")
    public String increaseStock() {
        shoppingService.test();
        return "finished";
    }

    @PostMapping("buy")
    public Result purchase() {
        Map<String, String> data = null;
        data = shoppingService.decreaseStock2();
        return new Result(data);
    }
}
