package com.erp.erp.employeeLevel;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EmployeeLevelResponse {
    Integer id;
    String level;
    String levelDescription;

}
