package com.mark.manager.exceptionHandler;

import com.mark.manager.bo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ResponseBody
@ControllerAdvice("com.mark.manager.controller")
@Component
public class ValidateExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handle(
            MethodArgumentNotValidException exception,
            HttpServletResponse response) {
        System.out.println("开始验证");
        BindingResult result  = exception.getBindingResult();
        StringBuffer errInfo = new StringBuffer();
        if (result.hasErrors()) {
            for(ObjectError e : result.getAllErrors()) {
                errInfo.append(e.getDefaultMessage());
                errInfo.append(",");
            }
        }
        return new Result(801, errInfo.toString());
    }
}
