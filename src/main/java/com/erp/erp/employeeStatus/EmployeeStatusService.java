package com.erp.erp.employeeStatus;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeStatusService {
    private final EmployeeStatusRepoistory employeeStatusRepository;

    public List<EmployeeStatusResponse> getAllEmployeeStatuses() {
        List<EmployeeStatus> all = employeeStatusRepository.findAll();
        return all.stream()
                .map(es -> EmployeeStatusMapper.employeeStatusToEmployeeStatusResponse(es))
                .collect(Collectors.toList());
    }

    public EmployeeStatusResponse getEmployeeStatusById(Integer id) {
        EmployeeStatus orElse = employeeStatusRepository.findById(id).orElse(null);
        if (orElse != null) {
            return EmployeeStatusMapper.employeeStatusToEmployeeStatusResponse(orElse);
        }
        throw new EmployeeStatusException("Employee status does not exist");
    }
    
    public EmployeeStatus getEmployeeStatusByIdOrElseThrowException(Integer id) {
        EmployeeStatus orElse = employeeStatusRepository.findById(id).orElse(null);
        if (orElse != null) {
            return (orElse);
        }
        throw new EmployeeStatusException("Employee status does not exist");
    }

    public boolean isEmployeeStatusExist(Integer id) {
        return employeeStatusRepository.findById(id).isPresent();
    }

    public boolean isEmployeeStatusExist(EmployeeStatusRequest employeeStatusRequest) {
        return employeeStatusRepository.findAll().stream()
                .filter(employeeStatus -> employeeStatus.getEmployeeStatus().equals(employeeStatusRequest.employeeStatus()))
                .findFirst()
                .isPresent();
    }
    public boolean isEmployeeStatusExist(EmployeeStatusRequest employeeStatusRequest,EmployeeStatus exis) {
        return employeeStatusRepository.findAll().stream()
                .filter(employeeStatus -> employeeStatus.getEmployeeStatus().equals(employeeStatusRequest.employeeStatus()) && exis.getId()!=employeeStatus.getId())
                .findFirst()
                .isPresent();
    }

    public void addEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) {
        if (!isEmployeeStatusExist(employeeStatusRequest)) {
            EmployeeStatus fromEmployeeStatusRequest = EmployeeStatusMapper.fromEmployeeStatusRequest(employeeStatusRequest);
        
            employeeStatusRepository.save(fromEmployeeStatusRequest);
        
        } else {
            throw new EmployeeStatusException("Employee status already exists");
        }
    }

    public EmployeeStatusResponse updateEmployeeStatus(Integer id, EmployeeStatusRequest employeeStatusRequest) {
        if (isEmployeeStatusExist(id)) {
            EmployeeStatus employeeStatus = employeeStatusRepository.findById(id).orElse(null);
         
            if(!isEmployeeStatusExist(employeeStatusRequest,employeeStatus)){
                var result = EmployeeStatusMapper.employeeStatusRequestToEmployeeStatus(employeeStatus, employeeStatusRequest);
            employeeStatusRepository.save(result);
            return EmployeeStatusMapper.employeeStatusToEmployeeStatusResponse(result);
            }
            else
            throw new EmployeeStatusException("Employee status already exists with this Status");
            
        }
        throw new EmployeeStatusException("Employee status does not exist with this ID");
    }

    public void deleteEmployeeStatus(Integer id) {
        if (isEmployeeStatusExist(id))
            employeeStatusRepository.deleteById(id);
        else
            throw new EmployeeStatusException("Employee status not found");
    }
}
