package com.erp.erp.moduleAndSubmodule;

import com.erp.erp.permission.PermissionModelMapper;

public class SubModuleMapper {
    public static SubModuleResponse subModuleToSubModuleResponse(SubModule subModule) {
        return new SubModuleResponse(
                subModule.getId(),
                subModule.getName(),
                ModuleMapper.moduleToModuleResponse(subModule.getModule()),
               PermissionModelMapper.permissionToPermissionResponse(subModule.getPermission())
        );
    }

    public static SubModule fromSubModuleRequest(SubModuleRequest subModuleRequest, Module module) {
        SubModule subModule = new SubModule();
        subModule.setName(subModuleRequest.name());
        subModule.setModule(module);
        return subModule;
    }
}
