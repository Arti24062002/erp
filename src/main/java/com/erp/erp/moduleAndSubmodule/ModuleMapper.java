package com.erp.erp.moduleAndSubmodule;


public class ModuleMapper {

    public static ModuleResponse moduleToModuleResponse(Module module) {
        return new ModuleResponse(
                module.getId(),
                module.getModuleName()
        );
    }

    public static Module fromModuleRequest(ModuleRequest moduleRequest) {
        Module module = new Module();
        module.setModuleName(moduleRequest.moduleName());
        return module;
    }
}