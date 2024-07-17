package com.erp.erp.EmployeeBankInformation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Bank name must not be blank")
    private String bank;

    @NotBlank(message = "IFSC code must not be blank")
    private String ifsc;

    @NotNull(message = "Bank account number must be provided")
    @Pattern(regexp = "^[0-9]{0,16}$", message = "Bank account number must be a maximum of 16 digits")
    private String number;

    @Min(value = 1, message = "User ID must be greater than 0")
    @Column(unique = true)
    private int userId;
}