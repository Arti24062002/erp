package com.erp.erp.employeeLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeSubLevelRequest(
        @NotBlank(message = "SubLevel is mandatory") String subLevel,
       
        @NotBlank(message = "SubLevelTitle is mandatory") String subLevelTitle,
        @NotBlank(message = "SubLevelDiscription is mandatory")

        String subLevelDescription,
        @NotBlank(message = "SubLevelQulificatioString is mandatory") String subLevelQualification,
        @NotNull(message = "EmployeeLevelId is mandatory") Integer employeeLevelId) {

}
