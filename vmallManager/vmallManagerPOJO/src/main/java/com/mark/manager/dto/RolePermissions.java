package com.mark.manager.dto;

import com.mark.manager.pojo.VproPermissions;

import java.io.Serializable;
import java.util.List;

public class RolePermissions implements Serializable {
    private Integer rolesPermissionsId;
    private Integer rolesId;
    private Integer permissionsId;
    private List<VproPermissions> vproPermissions;

    public List<VproPermissions> getVproPermissions() {
        return vproPermissions;
    }

    public void setVproPermissions(List<VproPermissions> vproPermissions) {
        this.vproPermissions = vproPermissions;
    }

    public RolePermissions() {
    }

    public Integer getRolesPermissionsId() {
        return rolesPermissionsId;
    }

    public void setRolesPermissionsId(Integer rolesPermissionsId) {
        this.rolesPermissionsId = rolesPermissionsId;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }
}
