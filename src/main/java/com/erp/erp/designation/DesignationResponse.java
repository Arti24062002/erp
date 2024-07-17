package com.erp.erp.designation;
import com.erp.erp.department.DepartmentResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignationResponse {
    Integer id;
    String designationName;
    @JsonBackReference
    DepartmentResponse department;
}
