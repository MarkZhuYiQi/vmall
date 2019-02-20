package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.constant.LoginConstant;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Login;
import com.mark.manager.service.AuthService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "login")
public class AuthController {

    @Reference()
    private
    AuthService authService;

    @PostMapping("")
    public Result login(@RequestBody Login login) {
        try{
            // 1. 检测得到的数据是否可以成功解密
            login = authService.decrypt(login);
            String errMessage = authService.verifyLogin(login);
            // 2. 如果传来的信息不合法，抛错返回
            if (errMessage != null) return new Result("user or password mismatch", LoginConstant.LOGIN_INFO_ILLEGAL);
            // 3. 生成token
            Result result = authService.genToken(login);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("AuthController.login: " + principal);
            return result;
        } catch(Exception e) {
            return new Result("user or password error", LoginConstant.PASS_MISMATCH);
        }
    }
    @PostMapping("test")
    public Result test(@RequestBody Login login) throws Exception {
        System.out.println(authService.decrypt(login));
        return new Result();
    }
//    @GetMapping("info")
//    public Result getInfo() {
//    }
}
