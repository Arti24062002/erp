package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erp.department.DepartmentService;
import com.erp.erp.designation.DesignationService;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class DesignationController {
    private final DesignationService designationService;
    private final DepartmentService departmentService;
    @GetMapping("/designations")
    public String getDesignations(Model m) {
        m.addAttribute("allDepartment", departmentService.getAllDepartment());
        m.addAttribute("allDesignation", designationService.getAlldesignation());
        return "designations";
    }
}
