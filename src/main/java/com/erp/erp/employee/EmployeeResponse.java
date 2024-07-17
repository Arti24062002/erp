package com.erp.erp.employee;

import java.sql.Date;
import java.util.List;

import com.erp.erp.department.Department;
import com.erp.erp.designation.Designation;
import com.erp.erp.employeeLevel.EmployeeSubLevel;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.permission.PermissionResponse;
import com.erp.erp.role.Role;
import com.erp.erp.shift.Shift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse{
    private Integer id;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String professionalEmail;
    private Long pPhoneNumbr;
    private Role roleId;
    private Date dob;
    private Date doj;
    private Designation designationId;
    private EmployeeSubLevel employeeSubLevelId;
    private Department departmentId;
    private String gender;
    private Shift shiftId;
    private EmployeeStatus employeeStatusId;
    private EmployeeStatus currentStatusId;
    private String company;
    private boolean status;
    private List<PermissionResponse> permissions;
    private String encodeUrl;
    private int rm;
    private int secondRm;

    
}
