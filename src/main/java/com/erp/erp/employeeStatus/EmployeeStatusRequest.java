package com.erp.erp.employeeStatus;

import jakarta.validation.constraints.*;

public record EmployeeStatusRequest(
                @PositiveOrZero(message = "Probation time in days cannot be negative") int probationTimeInDays,
                @NotBlank(message = "Employee status can't be blank") @NotNull(message = "Employee status can't be null") String employeeStatus,
                @NotBlank(message = "Description can't be blank") @NotNull(message = "Description can't be null") String description) {
}