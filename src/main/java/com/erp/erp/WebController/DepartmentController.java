package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erp.department.DepartmentService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    @GetMapping("/departments")
    public String getDepartment(Model m) {
        m.addAttribute("alldepartment", departmentService.getAllDepartment());
        return "departments";
    }
    
}
