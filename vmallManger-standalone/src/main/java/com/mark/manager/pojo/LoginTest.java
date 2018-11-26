package com.mark.manager.pojo;

import javax.validation.constraints.NotNull;

public class LoginTest {
    @NotNull
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

    public LoginTest() {
    }

    public LoginTest(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }
}
