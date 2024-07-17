package com.erp.erp.designation;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationRequest {
    @NotBlank(message = "DesignationName name cant blank")
    @NotNull(message = "DesignationName name cant null")
    String designationName;
    @PositiveOrZero
    @NotNull(message = "Department  name cant null")
    Integer departmentId;
    
}
