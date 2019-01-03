package com.mark.pojo;

import javax.validation.constraints.NotBlank;

public class Role {
    @NotBlank(message = "角色Id不能为空")
    private String rid;
    @NotBlank(message = "角色名不为空")
    private String roleName;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid='" + rid + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
