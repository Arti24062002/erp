package com.erp.erp.EmployeeBankInformation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankInfoRequest {
   
    @NotBlank(message = "Bank name must not be blank")
    private String bank;

    @NotBlank(message = "IFSC code must not be blank")
    private String ifsc;

    @NotNull(message = "Bank account number must be provided")
    @Pattern(regexp = "^[0-9]{0,16}$", message = "Bank account number must be a maximum of 16 digits")
    private String number;

    @Min(value = 1, message = "User ID must be greater than 0")
    private int userId;
}

