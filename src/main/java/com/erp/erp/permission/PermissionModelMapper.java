package com.erp.erp.permission;

public class PermissionModelMapper {
    public static PermissionResponse permissionToPermissionResponse(Permission permission){
        return new PermissionResponse(permission.getId(),permission.getAccessPermissionName(), permission.getUrl());
    }
}
