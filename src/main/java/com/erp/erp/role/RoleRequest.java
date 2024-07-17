package com.erp.erp.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(
        @NotBlank(message = "Role name can't be blank")
        @NotNull(message = "Role name can't be null")
        String role
       
) {}