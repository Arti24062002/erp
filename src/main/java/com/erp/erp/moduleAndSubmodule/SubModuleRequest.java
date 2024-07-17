package com.erp.erp.moduleAndSubmodule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SubModuleRequest(
        @NotBlank(message = "Sub-module name can't be blank") @NotNull(message = "Sub-module name can't be null") String name,
        @PositiveOrZero int moduleId) {

}
