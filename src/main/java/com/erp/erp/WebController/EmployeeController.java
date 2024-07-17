package com.erp.erp.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.erp.erp.department.DepartmentService;
import com.erp.erp.designation.DesignationService;
import com.erp.erp.employee.EmployeeException;
import com.erp.erp.employee.EmployeeRequest;
import com.erp.erp.employee.EmployeeResponse;
import com.erp.erp.employee.EmployeeService;
import com.erp.erp.employeeLevel.EmployeeSubLevelService;
import com.erp.erp.employeeStatus.EmployeeStatusService;
import com.erp.erp.role.RoleService;
import com.erp.erp.security.Base64Util;
import com.erp.erp.shift.ShiftService;

import lombok.RequiredArgsConstructor;

@Controller

@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final RoleService roleService;
    private final ShiftService shiftService;
    private final EmployeeStatusService employeeStatusService;
    private final EmployeeSubLevelService employeeSubLevelService;

    @GetMapping("/add-employee")
    public String addEmployee(Model m) {
        m.addAttribute("allEmployees", employeeService.getAllEmployees());
        m.addAttribute("allDepartments", departmentService.getAllDepartment());
        m.addAttribute("allDesignations", designationService.getAlldesignation());
        m.addAttribute("allRoles", roleService.getAllRoles());
        m.addAttribute("allEmployeeSubLevels", employeeSubLevelService.getAllEmployeeSubLevels());
        m.addAttribute("allShifts", shiftService.getAllShifts());
        m.addAttribute("allEmployeeStatus", employeeStatusService.getAllEmployeeStatuses());
        m.addAttribute("employee", new EmployeeRequest());
        return "add-employee";
    }

    @GetMapping("/employees")
    public String getEmployee(Model m) {
        m.addAttribute("allRoles", roleService.getAllRoles());
        m.addAttribute("allDesignations", designationService.getAlldesignation());
        m.addAttribute("allEmployeeSubLevels", employeeSubLevelService.getAllEmployeeSubLevels());
        m.addAttribute("allShifts", shiftService.getAllShifts());
        m.addAttribute("allEmployeeStatus", employeeStatusService.getAllEmployeeStatuses());
        m.addAttribute("allEmployees", employeeService.getAllEmployees());
        m.addAttribute("allDepartments", departmentService.getAllDepartment());

        return "employees";
    }

    @GetMapping("/employees-list")
    public String getEmployeeList(Model m) {
        m.addAttribute("allRoles", roleService.getAllRoles());
        m.addAttribute("allDesignations", designationService.getAlldesignation());
        m.addAttribute("allEmployeeSubLevels", employeeSubLevelService.getAllEmployeeSubLevels());
        m.addAttribute("allShifts", shiftService.getAllShifts());
        m.addAttribute("allEmployeeStatus", employeeStatusService.getAllEmployeeStatuses());
        m.addAttribute("allEmployees", employeeService.getAllEmployees());
        m.addAttribute("allDepartments", departmentService.getAllDepartment());

        return "employees-list";
    }

    @GetMapping("/")
    public String getDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/profile/{id}")
    public String getEmployeeProfile(@PathVariable String id, Model m) {
        try {
            int value = Integer.parseInt(Base64Util.decode(id));
            System.out.println("decoded value "+value);
            EmployeeResponse employeeResponseById = employeeService.getEmployeeResponseById(value);
            m.addAttribute("employee",employeeResponseById);
            System.out.println(employeeResponseById);
            EmployeeResponse rm=employeeService.getEmployeeResponseById(employeeResponseById.getRm());
            m.addAttribute("rm", rm);
            return "profile";
        } catch (Exception e) {
           throw new EmployeeException("Employee does not exist");
        }
        
    }
    @GetMapping("/assets")
    public String getAssests(Model m) {
       

        return "assets";
    }


}
