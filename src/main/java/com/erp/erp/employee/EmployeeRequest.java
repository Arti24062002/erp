package com.erp.erp.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Personal email should be valid")
    @NotBlank(message = "Personal email is mandatory")
    private String personalEmail;
    @Email(message = "Professional email should be valid")
    @NotBlank(message = "Professional email is mandatory")
    private String professionalEmail;

    @NotNull(message = "Personal phone number is mandatory")
    private Long phone;

    @NotNull(message = "Role ID is mandatory")
    private int roleId;
    @NotNull(message = "Date of Birth is mandatory")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @NotNull(message = "Date of joining is mandatory")
    @Temporal(TemporalType.DATE)
    private Date doj;

    @NotNull(message = "Designation ID is mandatory")
    private int designationId;

    @NotNull(message = "Employee level is mandatory")
    private int employeeSubLevelId;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "Shift ID is mandatory")
    private int shiftId;

    @NotNull(message = "Employee status ID is mandatory")
    private int employeeStatusId;
    @NotNull(message = "current status ID is mandatory")
    private int currentStatusId;

    @NotNull(message = "Department  ID is mandatory")
    @PositiveOrZero(message = "Department ID cant be zero or negative")
    private int departmentId;
    @NotBlank(message = "Company name is mandatory")
    private String company;

    @NotNull(message = "Status is mandatory")
    private boolean status;

    private List<Integer> permission;

    @NotNull(message = "RM value is mandatory")
    private int rm;

    @NotNull(message = "Second RM value is mandatory")
    private int secondRm;

}
