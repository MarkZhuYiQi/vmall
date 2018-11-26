package com.mark.common.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private String authId;
    private String authAppid;
    private String authAppkey;
    private String authIp;
    private Integer authRolesId;
    private String authRolesName;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "authId='" + authId + '\'' +
                ", authAppid='" + authAppid + '\'' +
                ", authAppkey='" + authAppkey + '\'' +
                ", authIp='" + authIp + '\'' +
                ", authRolesId=" + authRolesId +
                ", authRolesName='" + authRolesName + '\'' +
                '}';
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthAppid() {
        return authAppid;
    }

    public void setAuthAppid(String authAppid) {
        this.authAppid = authAppid;
    }

    public String getAuthAppkey() {
        return authAppkey;
    }

    public void setAuthAppkey(String authAppkey) {
        this.authAppkey = authAppkey;
    }

    public String getAuthIp() {
        return authIp;
    }

    public void setAuthIp(String authIp) {
        this.authIp = authIp;
    }

    public Integer getAuthRolesId() {
        return authRolesId;
    }

    public void setAuthRolesId(Integer authRolesId) {
        this.authRolesId = authRolesId;
    }

    public String getAuthRolesName() {
        return authRolesName;
    }

    public void setAuthRolesName(String authRolesName) {
        this.authRolesName = authRolesName;
    }
}
