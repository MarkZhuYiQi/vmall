package com.mark.manager.dto;

import java.io.Serializable;
import java.util.List;

public class UserRoles implements Serializable {
    private Long authId;
    private String authAppid;
    private String authAppkey;
    private String authTime;
    private String authIp;
    private Integer authRolesId;
    private List<Role> role;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
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

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
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

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public UserRoles() {
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "authId=" + authId +
                ", authAppid='" + authAppid + '\'' +
                ", authAppkey='" + authAppkey + '\'' +
                ", authTime='" + authTime + '\'' +
                ", authIp='" + authIp + '\'' +
                ", authRolesId=" + authRolesId +
                ", role=" + role +
                '}';
    }
}
