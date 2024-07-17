package com.erp.erp.employeeLevel;

public class EmployeeSubLevelMapper {
    public static EmployeeSubLevel toEntity(EmployeeSubLevelRequest request, EmployeeLevel employeeLevel) {
        EmployeeSubLevel employeeSubLevel = new EmployeeSubLevel();
        employeeSubLevel.setSubLevel(request.subLevel());
        employeeSubLevel.setSubLevelTitle(request.subLevelTitle());
        employeeSubLevel.setSubLevelDiscription(request.subLevelDescription());
        employeeSubLevel.setSubLevelQualification(request.subLevelQualification());
        employeeSubLevel.setEmployeeLevel(employeeLevel);
        return employeeSubLevel;
    }

    public static EmployeeSubLevelResponse toResponse(EmployeeSubLevel employeeSubLevel) {
        return new EmployeeSubLevelResponse(
            employeeSubLevel.getId(),
            employeeSubLevel.getSubLevel(),
            employeeSubLevel.getSubLevelTitle(),
            employeeSubLevel.getSubLevelDiscription(),
            employeeSubLevel.getSubLevelQualification(),
            EmployeeLevelMapper.employeeLevelToEmployeeLevelResponse(  employeeSubLevel.getEmployeeLevel())
        );
    }
}
