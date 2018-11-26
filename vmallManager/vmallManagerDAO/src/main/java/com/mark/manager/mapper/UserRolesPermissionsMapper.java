package com.mark.manager.mapper;

import com.mark.manager.dto.UserRoles;
import org.apache.ibatis.annotations.Param;

public interface UserRolesPermissionsMapper {
    public UserRoles getUserRolesPermissions(@Param("appId") String appId);
    public UserRoles getUserRoles(@Param("appId") String appId);
}
