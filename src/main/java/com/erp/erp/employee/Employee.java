package com.erp.erp.employee;

import java.sql.Date;
import java.util.List;

import com.erp.erp.department.Department;
import com.erp.erp.designation.Designation;
import com.erp.erp.employeeLevel.EmployeeSubLevel;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.permission.Permission;
import com.erp.erp.role.Role;
import com.erp.erp.shift.Shift;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {
  @Id
  // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
  // "sequence_generator")
  // @SequenceGenerator(name = "sequence_generator", sequenceName = "user-id",
  // initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer userId;
  @Column
  String firstName;
  @Column
  String lastName;

  @Column(unique = true)
  private String personalEmail;
  @Column(unique = true)
  private String professionalEmail;
  @Column(unique = true)
  private Long phone;
  @ManyToOne

  Role role;
  Date dob;
  Date doj;
  @ManyToOne

  Designation designation;
  @ManyToOne

  EmployeeSubLevel employeeSubLevel;
  String gender;
  @ManyToOne

  Department department;
  @ManyToOne()

  Shift shift;
  @ManyToOne
  EmployeeStatus employeeStatus;

  @ManyToOne
  EmployeeStatus currentStatus;
  String company;
  boolean status;
  @ManyToMany()
  List<Permission> permission;
  byte[] userImage;
  int rm;
  int secondRm;

}
