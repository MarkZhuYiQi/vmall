package com.mark.manager.bo;

import java.io.Serializable;

public class TokenResult implements Serializable {
    private String role;
    private String token;
    private String message;

    public TokenResult() {
    }

    public TokenResult(String role, String token, String message) {
        this.role = role;
        this.token = token;
        this.message = message;
    }

    public TokenResult(String role, String token) {
        this.role = role;
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
