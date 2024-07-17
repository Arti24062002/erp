package com.erp.erp.employeeLevel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeLevelService {
    final EmployeeLevelRepository employeeLevelRepository;

    public List<EmployeeLevelResponse> getAllEmployeeLevels() {
        List<EmployeeLevel> all = employeeLevelRepository.findAll();
        return all.stream()
                .map(el -> EmployeeLevelMapper.employeeLevelToEmployeeLevelResponse(el))
                .collect(Collectors.toList());
    }

    public EmployeeLevelResponse getEmployeeLevelById(Integer id) {
        EmployeeLevel orElse = employeeLevelRepository.findById(id).orElse(null);
        if (orElse != null) {
            return EmployeeLevelMapper.employeeLevelToEmployeeLevelResponse(orElse);
        }
        throw new EmployeeLevelException("Employee level does not exist");
    }

    public EmployeeLevel getEmployeeLevelByIdOrElseThrow(Integer id) {
        EmployeeLevel orElse = employeeLevelRepository.findById(id).orElse(null);
        if (orElse != null) {
            return orElse;
        }
        throw new EmployeeLevelException("Employee level does not exist");
    }
    public boolean isEmployeeLevelExist(Integer id) {
        return employeeLevelRepository.findById(id).isPresent();
    }

    public boolean isEmployeeLevelExist(EmployeeLevelRequest employeeLevelRequest,EmployeeLevel exisEmployeeLevel) {
        return employeeLevelRepository.findAll().stream()
                .filter(employeeLevel -> employeeLevel.getLevel().equals(employeeLevelRequest.getLevel())&& exisEmployeeLevel.getId()!=employeeLevel.getId())
                .findFirst()
                .isPresent();
    }
    public boolean isEmployeeLevelExist(EmployeeLevelRequest employeeLevelRequest) {
        return employeeLevelRepository.findAll().stream()
                .filter(employeeLevel -> employeeLevel.getLevel().equals(employeeLevelRequest.getLevel()))
                .findFirst()
                .isPresent();
    }


    public void addEmployeeLevel(EmployeeLevelRequest employeeLevelRequest) {
        if (!isEmployeeLevelExist(employeeLevelRequest)) {
            EmployeeLevel fromEmployeeLevelRequest = EmployeeLevelMapper.fromEmployeeLevelRequest(employeeLevelRequest);
            employeeLevelRepository.save(fromEmployeeLevelRequest);
        } else {
            throw new EmployeeLevelException("Employee level already exists");
        }
    }

    public EmployeeLevelResponse updateEmployeeLevel(Integer id, EmployeeLevelRequest employeeLevelRequest) {
        if (isEmployeeLevelExist(id)) {
            EmployeeLevel employeeLevel = employeeLevelRepository.findById(id).orElse(null);

            if (!isEmployeeLevelExist(employeeLevelRequest,employeeLevel)) {
                EmployeeLevel result = EmployeeLevelMapper.employeeLevelRequestToEmployeeLevel(employeeLevel,
                        employeeLevelRequest);
                employeeLevelRepository.save(result);
                return EmployeeLevelMapper.employeeLevelToEmployeeLevelResponse(result);
            }
            throw new EmployeeLevelException("Employee level already   exist with this ID");
        }
        throw new EmployeeLevelException("Employee level does not exist with this ID");
    }

    public void deleteEmployeeLevel(Integer id) {
        if (isEmployeeLevelExist(id))
            employeeLevelRepository.deleteById(id);
        else
            throw new EmployeeLevelException("Employee level not found");
    }
}
