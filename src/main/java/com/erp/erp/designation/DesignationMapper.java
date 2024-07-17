package com.erp.erp.designation;

import com.erp.erp.department.Department;
import com.erp.erp.department.DepartmentMapper;

public class DesignationMapper {
    public static Designation fromDesignationRequest(DesignationRequest designationRequest, Department department) {
        Designation designation = new Designation();
        designation.setDesignationName(designationRequest.getDesignationName());
        designation.setDepartment(department);
        return designation;
    }

    public static Designation designationRequestToDesignation(Designation designation,
            DesignationRequest designationRequest, Department department) {

        designation.setDesignationName(designationRequest.getDesignationName());

        designation.setDepartment(department);
        return designation;
    }

    public static DesignationResponse designationToDesignationResponse(Designation designation) {
        return DesignationResponse.builder().id(designation.getId()).designationName(designation.getDesignationName())
                .department(DepartmentMapper.departmentToDepartmentResponse(designation.getDepartment()))
                .build();

    }
}
