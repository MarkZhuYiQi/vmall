package com.mark.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mark.common.constant.LoginConstant;
import com.mark.common.exception.AuthException;
import com.mark.common.util.IpUtil;
import com.mark.manager.bo.Result;
import com.mark.manager.dto.Login;
import com.mark.manager.pojo.VproAuth;
import com.mark.manager.service.AuthService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "login")
public class AuthController {

    @Reference()
    private
    AuthService authService;

    @PostMapping("")
    public Result login(@RequestBody Login login, HttpServletRequest httpServletRequest) {
        try{
            // 1. 检测得到的数据是否可以成功解密
            login = authService.decrypt(login);
            String errMessage = authService.verifyLogin(login);
            // 2. 如果传来的信息不合法，抛错返回
            if (errMessage != null) return new Result(LoginConstant.LOGIN_INFO_ILLEGAL, "user or password mismatch!");
            // 3. 生成token
            String ip = IpUtil.getIpAddress(httpServletRequest);
            Result result = authService.genToken(login, ip);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("AuthController.login: " + principal);
            return result;
        } catch(Exception e) {
            return new Result(LoginConstant.PASS_MISMATCH, "user or password error!" + e.getMessage());
        }
    }
    @GetMapping("")
    public Result getLoginInfo(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("MyToken");
        try {
            VproAuth auth = authService.getLoginInfo(token);
            return new Result(auth);
        } catch (AuthException e) {
            return new Result(e.getCode(), e.getMsg());
        }
    }
    @GetMapping("out")
    public void logOut(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("MyToken");
        authService.logOut(token);
    }

    @PostMapping("test")
    public Result test(@RequestBody Login login) throws Exception {
        System.out.println(authService.decrypt(login));
        return new Result();
    }

}
