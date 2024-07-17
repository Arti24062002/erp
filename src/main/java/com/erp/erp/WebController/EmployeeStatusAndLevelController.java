package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.erp.erp.employeeLevel.EmployeeLevelService;
import com.erp.erp.employeeLevel.EmployeeSubLevelService;
import com.erp.erp.employeeStatus.EmployeeStatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeStatusAndLevelController {
   
    private final EmployeeStatusService employeeStatusService;
    private final EmployeeLevelService employeeLevelService;
    private final EmployeeSubLevelService employeeSubLevelService;
    @GetMapping("/employee-status")
    public String getEmployeeStatus(Model m){
        m.addAttribute("allEmployeeStatus", employeeStatusService.getAllEmployeeStatuses());
        return "employee-status";
    }
    @GetMapping("/employee-level")
    public String getEmployeeLevel(Model m){
        m.addAttribute("allEmployeeSubLevel", employeeSubLevelService.getAllEmployeeSubLevels());
        m.addAttribute("allEmployeeLevel", employeeLevelService.getAllEmployeeLevels());
        return "employee-level";
    }
}
