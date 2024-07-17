package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;

import com.erp.erp.moduleAndSubmodule.ModuleService;
import com.erp.erp.moduleAndSubmodule.SubModuleService;

import com.erp.erp.role.RoleResponse;
import com.erp.erp.role.RoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;;

@Controller
@RequiredArgsConstructor
public class RoleAndPermissionController {
    private final RoleService roleService;
    private final ModuleService moduleService;
    private final SubModuleService subModuleService;
    @GetMapping("/role-permission")
    public String getRoles(Model m) {
        m.addAttribute("allroles", roleService.getAllRoles());
        m.addAttribute("allmodules", moduleService.getAllModules());
        m.addAttribute("allsubmodules",subModuleService.getAllSubModules());
        return "role-permission";
    }
    @GetMapping("/get-module-By-UserRole/{id}")
    public String getModulePage(@PathVariable Integer id,Model m){
        RoleResponse role=roleService.getRoleResponseById(id);
        m.addAttribute("role", role);
        m.addAttribute("allmodules", moduleService.getAllModules());
        m.addAttribute("allsubmodules",subModuleService.getAllSubModules());
        return "module-by-role";
     
    }

    
}
