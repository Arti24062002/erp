package com.erp.erp.employeeLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeLevelRequest {
    @NotBlank(message = "Leval   cant blank")
    @NotNull(message = "Level cant null")
    String level;
    @NotBlank(message = "LevelDescription name cant blank")
    @NotNull(message = "LevelDescription name cant null")
    String levelDescription;

}
