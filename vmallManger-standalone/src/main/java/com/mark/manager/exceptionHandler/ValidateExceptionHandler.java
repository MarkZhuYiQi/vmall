package com.mark.manager.exceptionHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
@Component
public class ValidateExceptionHandler {
    @ExceptionHandler
    public String handle(ConstraintViolationException exception, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuffer errInfo = new StringBuffer();
        for(ConstraintViolation<?> item : violations) {
            errInfo.append(item.getMessage());
            errInfo.append(",");
        }
        return "失败: " + errInfo.toString();
    }
}
