package com.erp.erp.moduleAndSubmodule;

import com.erp.erp.permission.PermissionResponse;

public record SubModuleResponse(
        Integer id,
        String name,
        ModuleResponse module,
        PermissionResponse permission
       
) {}