package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.erp.erp.employee.EmployeeResponse;
import com.erp.erp.employee.EmployeeService;
import com.erp.erp.moduleAndSubmodule.ModuleService;
import com.erp.erp.moduleAndSubmodule.SubModuleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserAndPermissionController {
    private final EmployeeService employeeService;
    private final ModuleService moduleService;
    private final SubModuleService subModuleService;

     @GetMapping("/user-permission")
    public String getRoles(Model m) {
       
        m.addAttribute("allmodules", moduleService.getAllModules());
        m.addAttribute("allsubmodules",subModuleService.getAllSubModules());
        return "user-permission";
    }
    @GetMapping("/get-module-By-employee/{id}")
    public String getModulePage(@PathVariable Integer id,Model m){
        EmployeeResponse employee=employeeService.getEmployeeResponseById(id);
        m.addAttribute("employee", employee);
        m.addAttribute("allmodules", moduleService.getAllModules());
        m.addAttribute("allsubmodules",subModuleService.getAllSubModules());
        return "module-by-user";
     
    }

}
