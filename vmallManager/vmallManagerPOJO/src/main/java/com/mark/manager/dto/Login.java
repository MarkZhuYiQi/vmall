package com.mark.manager.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class Login implements Serializable {
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 30)
    @Pattern(regexp = "[A-Za-z]+")
    private String appId;
    @NotNull
    private String appKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Login() {
    }

    public Login(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

    @Override
    public String toString() {
        return "Login{" +
                "appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                '}';
    }
}
