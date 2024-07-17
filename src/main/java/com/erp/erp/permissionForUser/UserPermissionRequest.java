package com.erp.erp.permissionForUser;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record UserPermissionRequest(
        @NotNull(message = "Role Id Cant Null") Integer userId,
        @NotNull(message = "ucheckedPermissionIds cant null")
        List<Integer> checkedPermissionIds ,
        @NotNull(message = "unCheckedPermissionIds cant null")
        List<Integer> unCheckedPermissionIds) {
}
