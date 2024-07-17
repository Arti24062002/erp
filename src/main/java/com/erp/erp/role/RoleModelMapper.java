package com.erp.erp.role;

import java.util.stream.Collectors;

import com.erp.erp.permission.PermissionModelMapper;

public class RoleModelMapper {

    public static RoleResponse roleToRoleResponse(Role role) {
        return new RoleResponse(
                role.getId(), role.getRole(), role.getPermission().stream()
                        .map(PermissionModelMapper::permissionToPermissionResponse).collect(Collectors.toList()));
    }

    public static Role fromRoleRequest(RoleRequest roleRequest) {
        Role role = new Role();
        role.setRole(roleRequest.role().toUpperCase());
       
        return role;
    }
}
