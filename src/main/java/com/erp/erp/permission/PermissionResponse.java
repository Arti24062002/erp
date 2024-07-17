package com.erp.erp.permission;

public record PermissionResponse(
    int id,
    String accessPermissionName,
    String url
) {
    
}
