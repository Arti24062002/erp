package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erp.moduleAndSubmodule.ModuleService;
import com.erp.erp.moduleAndSubmodule.SubModuleService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ModuleAndModulePermission {
    private final ModuleService moduleService;
    private final SubModuleService subModuleService;
    @GetMapping("/module")
    public String getAllModule(Model m) {
        m.addAttribute("allmodules", moduleService.getAllModules());
        m.addAttribute("allsubmodules", subModuleService.getAllSubModules());
        return "module-settings";
    }
    
}
