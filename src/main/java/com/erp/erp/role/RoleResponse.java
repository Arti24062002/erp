package com.erp.erp.role;
import java.util.List;

import com.erp.erp.permission.PermissionResponse;
public record RoleResponse(
        Integer id,
        String role,
        List<PermissionResponse> permissions
) {}