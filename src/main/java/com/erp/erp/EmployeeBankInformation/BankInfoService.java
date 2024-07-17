package com.erp.erp.EmployeeBankInformation;

import org.springframework.stereotype.Service;

import com.erp.erp.employee.Employee;
import com.erp.erp.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankInfoService {

    private final BankInfoRepoistery bankInfoRepository;
    private final EmployeeService employeeService;

    public BankInfoResponse addBankInfo(BankInfoRequest request) {
        Employee employee = employeeService.getEmployeeByIdOrElseThrowException(request.getUserId());
        bankInfoRepository.findByUserId(request.getUserId()).orElseThrow(()->new BankInfoException("User can have only one bank information"));
        
        if (employee != null) {
            BankInfo bankInfo = BankInfoModelMapper.mapToBankInfo(request);
            return BankInfoModelMapper.mapToBankInfoResponse(bankInfoRepository.save(bankInfo));
        }

        throw new BankInfoException("Some Exception occured in adding Bank Info ");

    }

    public BankInfoResponse updateBankInfo(Integer id, BankInfoRequest request) {
        Employee employee = employeeService.getEmployeeByIdOrElseThrowException(request.getUserId());

        if (employee != null) {
            BankInfo existing = bankInfoRepository.findById(id)
                    .orElseThrow(() -> new BankInfoException("BankInfo not found with id: " + id));

            BankInfo updated = BankInfoModelMapper.updateBankInfo(existing, request);
            return BankInfoModelMapper.mapToBankInfoResponse(bankInfoRepository.save(updated));
        }

        throw new BankInfoException("Some Exception occured in adding Bank Info ");

    }

    public void deleteBankInfo(Integer id) {
        bankInfoRepository.findById(id)
                .orElseThrow(() -> new BankInfoException("BankInfo not found with id: " + id));
        bankInfoRepository.deleteById(id);
    }

    public List<BankInfoResponse> getAllBankInfo() {
        return bankInfoRepository.findAll().stream().map(BankInfoModelMapper::mapToBankInfoResponse).collect(Collectors.toList());
    }

    public BankInfo getBankInfoById(Integer id) {
        return bankInfoRepository.findById(id)
                .orElseThrow(() -> new BankInfoException("BankInfo not found with id: " + id));
    }
    public BankInfoResponse getBankInfoResponseById(Integer id) {
        return BankInfoModelMapper.mapToBankInfoResponse(bankInfoRepository.findById(id)
        .orElseThrow(() -> new BankInfoException("BankInfo not found with id: " + id)));
    }

    public Object getBankInfoById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBankInfoById'");
    }
}
