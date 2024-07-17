package com.erp.erp.employeeStatus;


public class EmployeeStatusMapper {

    public static EmployeeStatus fromEmployeeStatusRequest(EmployeeStatusRequest employeeStatusRequest) {
        EmployeeStatus employeeStatus = new EmployeeStatus();
        employeeStatus.setProbationTimeinDays(employeeStatusRequest.probationTimeInDays());
        employeeStatus.setEmployeeStatus(employeeStatusRequest.employeeStatus());
        employeeStatus.setDescription(employeeStatusRequest.description());
        return employeeStatus;
    }

    public static EmployeeStatus employeeStatusRequestToEmployeeStatus(EmployeeStatus employeeStatus, EmployeeStatusRequest employeeStatusRequest) {
        employeeStatus.setProbationTimeinDays(employeeStatusRequest.probationTimeInDays());
        employeeStatus.setEmployeeStatus(employeeStatusRequest.employeeStatus());
        employeeStatus.setDescription(employeeStatusRequest.description());
        return employeeStatus;
    }

    public static EmployeeStatusResponse employeeStatusToEmployeeStatusResponse(EmployeeStatus employeeStatus) {
        return new EmployeeStatusResponse(
                employeeStatus.getId(),
                employeeStatus.getProbationTimeinDays(),
                employeeStatus.getEmployeeStatus(),
                employeeStatus.getDescription()
        );
    }
}
