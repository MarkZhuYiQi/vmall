package com.mark.manager.controller;

import com.mark.manager.dto.TokenError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorController {
    @GetMapping("/badToken")
    public TokenError badToken(HttpServletResponse response)
    {
        TokenError tokenError = new TokenError();
        tokenError.setCode(String.valueOf(response.getStatus()));
        tokenError.setMessage("bad token");
        return tokenError;
    }
}
