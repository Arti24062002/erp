package com.erp.erp.department;

import java.util.List;

import com.erp.erp.designation.Designation;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
  Integer id;
  String departmentName;
  @JsonBackReference
  List<Designation> designation;

}
