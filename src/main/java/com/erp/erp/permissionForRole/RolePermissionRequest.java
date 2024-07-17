package com.erp.erp.permissionForRole;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record RolePermissionRequest(
        @NotNull(message = "Role Id Cant Null") Integer roleId,
        @NotNull(message = "ucheckedPermissionIds cant null")
        List<Integer> checkedPermissionIds ,
        @NotNull(message = "unCheckedPermissionIds cant null")
        List<Integer> unCheckedPermissionIds) {
}
