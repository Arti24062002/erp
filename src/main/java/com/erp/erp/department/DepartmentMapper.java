package com.erp.erp.department;

public class DepartmentMapper {
    public static Department fromDepartmentRequest(DepartmentRequest departementRequest){
        Department department=new Department();
        department.setDpartmentName(departementRequest.getDepartmentName());
      
        return department;
    }
    public static Department departmentRequestToDepartment(Department department,DepartmentRequest departementRequest){
        
        department.setDpartmentName(departementRequest.getDepartmentName());
        return department;
    }
    public static DepartmentResponse departmentToDepartmentResponse(Department departement){
        DepartmentResponse departmentResponse=new DepartmentResponse();
        departmentResponse.setId(departement.getId());
        departmentResponse.setDepartmentName(departement.getDpartmentName());
        departmentResponse.setDesignation(departement.getDesignation());
        return departmentResponse;
    }
}
