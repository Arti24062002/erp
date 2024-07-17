package com.erp.erp.employee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.erp.erp.department.Department;
import com.erp.erp.designation.Designation;
import com.erp.erp.employeeLevel.EmployeeSubLevel;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.permission.Permission;
import com.erp.erp.permission.PermissionModelMapper;
import com.erp.erp.role.Role;
import com.erp.erp.security.Base64Util;
import com.erp.erp.shift.Shift;

@Component
public class EmployeeMapper {

    public Employee mapToEmployee(EmployeeRequest request, Role role, List<Permission> permission,
            EmployeeStatus employeeStatus, EmployeeStatus currentStatus, EmployeeSubLevel level, Designation designation,
            Department department, Shift shift) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .personalEmail(request.getPersonalEmail())
                .professionalEmail(request.getProfessionalEmail())
                .phone(request.getPhone())
                .role(role)
                .dob(request.getDob())
                .doj(request.getDoj())
                .designation(designation)
                .employeeSubLevel(level) // Assuming EmployeeLevel has a method to get from ID
                .department(department)
                .currentStatus(currentStatus)
                .gender(request.getGender())
                .shift(shift)
                .employeeStatus(employeeStatus)
                .company(request.getCompany())
                .status(request.isStatus())
                .permission(permission)
                .rm(request.getRm())
                .secondRm(request.getSecondRm())
                .build();
    }

    public static EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getUserId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())

                .personalEmail(employee.getPersonalEmail())
                .professionalEmail(employee.getProfessionalEmail())
                .pPhoneNumbr(employee.getPhone())
                .roleId(employee.getRole())
                .dob(employee.getDob())
                .doj(employee.getDoj())
                .designationId(employee.getDesignation())
                .employeeSubLevelId(employee.getEmployeeSubLevel())
                .currentStatusId(employee.getCurrentStatus())
                .gender(employee.getGender())
                .shiftId(employee.getShift())
                .employeeStatusId(employee.getEmployeeStatus())
                .departmentId(employee.getDepartment())
                .company(employee.getCompany())
                .status(employee.isStatus())
                .permissions(employee.getPermission().stream().map(PermissionModelMapper::permissionToPermissionResponse).collect(Collectors.toList()))
                .rm(employee.getRm())
                .secondRm(employee.getSecondRm())
                .encodeUrl(Base64Util.encode(employee.getUserId().toString()))
                .build();
    }

    public Employee mapFromExistingEmployeeToEmployee(Employee existingEmployee, EmployeeRequest request, Role role,
            List<Permission> permissions, EmployeeStatus employeeStatus, EmployeeStatus currentStatus,
            EmployeeSubLevel employeeLevel,
            Designation designation, Department department, Shift shift) {
        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setPersonalEmail(request.getPersonalEmail());
        existingEmployee.setProfessionalEmail(request.getProfessionalEmail());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setRole(role);
        existingEmployee.setDob(request.getDob());
        existingEmployee.setDoj(request.getDoj());
        existingEmployee.setDesignation(designation);
        existingEmployee.setEmployeeSubLevel(employeeLevel);
        existingEmployee.setCurrentStatus(currentStatus);
        existingEmployee.setGender(request.getGender());
        existingEmployee.setShift(shift);
        existingEmployee.setEmployeeStatus(employeeStatus);
        existingEmployee.setDepartment(department);
        existingEmployee.setCompany(request.getCompany());
        existingEmployee.setStatus(request.isStatus());
        existingEmployee.setPermission(permissions);
        existingEmployee.setRm(request.getRm());
        existingEmployee.setSecondRm(request.getSecondRm());
        return existingEmployee;
    }
}
