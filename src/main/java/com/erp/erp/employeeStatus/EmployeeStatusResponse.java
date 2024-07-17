package com.erp.erp.employeeStatus;

public record EmployeeStatusResponse(
        Integer id,
        int probationTimeInDays,
        String employeeStatus,
        String description
) {}