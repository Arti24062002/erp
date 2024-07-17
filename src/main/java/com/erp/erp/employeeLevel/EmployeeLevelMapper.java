package com.erp.erp.employeeLevel;

public class EmployeeLevelMapper {

    public static EmployeeLevel fromEmployeeLevelRequest(EmployeeLevelRequest employeLevelRequest) {
        EmployeeLevel employee = new EmployeeLevel();
        employee.setLevel(employeLevelRequest.getLevel());
        employee.setLevelDescription(employeLevelRequest.getLevelDescription());
      

        return employee;
    }

    public static EmployeeLevelResponse employeeLevelToEmployeeLevelResponse(EmployeeLevel el) {
        return EmployeeLevelResponse.builder()
                .id(el.getId()).level(el.getLevel()).levelDescription(el.getLevelDescription()).build();
               
    }
    public static EmployeeLevel employeeLevelRequestToEmployeeLevel(EmployeeLevel employeeLevel, EmployeeLevelRequest employeeLevelRequest) {
        employeeLevel.setLevel(employeeLevelRequest.getLevel());
        employeeLevel.setLevelDescription(employeeLevelRequest.getLevelDescription());
    
        return employeeLevel;
    }

}