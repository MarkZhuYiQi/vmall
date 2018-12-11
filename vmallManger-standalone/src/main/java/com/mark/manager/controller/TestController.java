package com.mark.manager.controller;

import com.mark.manager.bo.Result;
import com.mark.manager.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    @PostMapping("buy/{id:\\d+}")
    public Result purchase(@PathVariable("id") Integer id, @RequestParam("name")String name) {
        Map<String, String> data = null;
        switch (id) {
            case 1:
                data = shoppingService.decreaseStock1();
                break;
            case 2:
                data = shoppingService.decreaseStock2();
                break;
            case 3:
                data = shoppingService.decreaseStock3(name);
                break;
        }
        return new Result(data);
    }
}
