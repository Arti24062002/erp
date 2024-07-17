package com.erp.erp.moduleAndSubmodule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModuleRequest(
        @NotBlank(message = "module name can't be blank") @NotNull(message = "module name can't be null") String moduleName     
        ) {

}
