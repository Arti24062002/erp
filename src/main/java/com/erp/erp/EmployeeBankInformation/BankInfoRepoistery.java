package com.erp.erp.EmployeeBankInformation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BankInfoRepoistery extends JpaRepository<BankInfo,Integer>{
    public Optional<BankInfo>  findByUserId(int userId);
}
