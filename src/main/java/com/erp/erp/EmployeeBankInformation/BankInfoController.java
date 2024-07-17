package com.erp.erp.EmployeeBankInformation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class BankInfoController {

    
    private final BankInfoService bankInfoService;

    @PostMapping("/add-bank-information")
    public ResponseEntity<BankInfoResponse> addBankInfo(@Valid @RequestBody BankInfoRequest request) {
       
        return ResponseEntity.status(HttpStatus.CREATED).body(bankInfoService.addBankInfo(request));
    }

    @PutMapping("/update-bank-information/{id}")
    public ResponseEntity<BankInfoResponse> updateBankInfo(
            @PathVariable Integer id,
            @Valid @RequestBody BankInfoRequest request) {
   
        return ResponseEntity.ok( bankInfoService.updateBankInfo(id, request));
    }

    @DeleteMapping("/delete-bank-information/{id}")
    public ResponseEntity<Void> deleteBankInfo(@PathVariable Integer id) {
        bankInfoService.deleteBankInfo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-bank-infomartion")
    public ResponseEntity<List<BankInfoResponse>> getAllBankInfo() {
        return ResponseEntity.ok(bankInfoService.getAllBankInfo());
    }

    @GetMapping("/get-bank-infomartion/{id}")
    public ResponseEntity<BankInfoResponse> getBankInfoById(@PathVariable Integer id) {
        
        return ResponseEntity.ok(bankInfoService.getBankInfoResponseById(id));
    }
}
