package com.erp.erp.shift;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ShiftRequest(
        @NotBlank(message = "Shift name is mandatory") String shiftName,

        @NotBlank(message = "Shift code is mandatory") String shiftCode,

        @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "Invalid time format. Expected format is HH:mm:ss") String loginTime,

        @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$", message = "Invalid time format. Expected format is HH:mm:ss")

        String logoutTime,
        @Min(value = 0, message = "Break value must be greater than zero") @Max(value = 60, message = "Break cant exceed above than 1 hour")

        int breakInMinute,
        @NotNull(message = "please select satoff option") String satOff,
        @NotNull(message = "please select sunoff option") boolean sunOff,

        @Min(value = 1, message = "Number of shifts must be at least 1") int noOfSessions,

        @Min(value = 0, message = "Extra hours to work cannot be negative") int extraTimeToWorkInHours,

        boolean status) {
}