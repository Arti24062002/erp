package com.erp.erp.EmployeeBankInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankInfoResponse {

    private Long id;
    private String bank;
    private String ifsc;
    private String number;
    private int userId;
}
