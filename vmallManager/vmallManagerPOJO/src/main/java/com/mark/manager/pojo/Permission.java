package com.mark.manager.pojo;

import java.io.Serializable;

public class Permission implements Serializable {
    private Integer permissionsId;
    private String permissionsName;

    public Permission() {
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }
}
