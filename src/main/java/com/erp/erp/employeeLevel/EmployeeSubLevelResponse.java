package com.erp.erp.employeeLevel;



public record EmployeeSubLevelResponse(
    Integer id,
    String subLevel,
    String subLevelTitle,
    String subLevelDescription,
    String subLevelQualification,
   
    EmployeeLevelResponse employeeLevelResponse
) {
} 
