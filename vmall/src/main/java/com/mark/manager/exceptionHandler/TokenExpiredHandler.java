package com.mark.manager.exceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mark.common.constant.AuthConstant;
import com.mark.manager.bo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/*@ResponseBody
@ControllerAdvice
@Component
public class TokenExpiredHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JWTVerificationException.class)
    public Result handle(JWTVerificationException exception, HttpServletResponse response) {
        return new Result(AuthConstant.JWT_TOKEN_EXPIRED, exception.getMessage());
    }
}*/
