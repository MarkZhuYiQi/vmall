package com.mark.controller;

import com.mark.pojo.Result;
import com.mark.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    Validator localValidator;
    @PostMapping("")
    public @ResponseBody Result userTest(@RequestBody User user) {
        DataBinder dataBinder = new DataBinder(user);
        dataBinder.setValidator(localValidator);
        dataBinder.validate();
        BindingResult bindingResult = dataBinder.getBindingResult();
        System.out.println(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            for(ObjectError err : bindingResult.getAllErrors()) {
                System.out.println(err.getDefaultMessage());
            }
        }
        Result result = new Result();
        result.setData("err");
        return result;
    }
}
