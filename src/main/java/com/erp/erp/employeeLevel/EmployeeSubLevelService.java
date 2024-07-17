package com.erp.erp.employeeLevel;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeSubLevelService {

    private final EmployeeSubLevelRepoistery employeeSubLevelRepository;
    private final EmployeeLevelService employeeLevelService;

    public List<EmployeeSubLevelResponse> getAllEmployeeSubLevels() {
        List<EmployeeSubLevel> all = employeeSubLevelRepository.findAll();
        return all.stream().map(EmployeeSubLevelMapper::toResponse).toList();
    }

    public List<EmployeeSubLevelResponse> getEmployeeSubLevelsByEmployeeLevelId(Integer id) {
        EmployeeLevel employeeLevel = employeeLevelService.getEmployeeLevelByIdOrElseThrow(id);
        return employeeSubLevelRepository.findAll().stream().filter(fn->fn.getEmployeeLevel().id==employeeLevel.getId()).map(EmployeeSubLevelMapper::toResponse).toList();
    }

    public EmployeeSubLevelResponse getEmployeeSubLevelById(Integer id) {
        EmployeeSubLevel employeeSubLevel = employeeSubLevelRepository.findById(id)
                .orElseThrow(() -> new EmployeeSubLevelException("Employee sub-level not found with ID: " + id));
        return EmployeeSubLevelMapper.toResponse(employeeSubLevel);
    }

    public EmployeeSubLevel getEmployeeSubLevelByIdOrElseThrowException(Integer id) {
        return employeeSubLevelRepository.findById(id)
                .orElseThrow(() -> new EmployeeSubLevelException("Employee sub-level not found with ID: " + id));
    }

    public boolean isEmployeeSubLevelExist(Integer id) {
        return employeeSubLevelRepository.findById(id).isPresent();
    }

    public boolean isEmployeeSubLevelExist(EmployeeSubLevelRequest request) {
        return employeeSubLevelRepository.findAll().stream()
                .anyMatch(subLevel -> subLevel.getSubLevel().equals(request.subLevel()));
    }

    public boolean isEmployeeSubLevelExist(EmployeeSubLevelRequest request, EmployeeSubLevel existSubLevel) {
        return employeeSubLevelRepository.findAll().stream()
                .filter(subLevel -> subLevel.getSubLevel().equals(request.subLevel()) && !subLevel.getId().equals(existSubLevel.getId())).findFirst().isPresent();
    }

    public void addEmployeeSubLevel(EmployeeSubLevelRequest request) {
        EmployeeLevel employeeLevel = employeeLevelService.getEmployeeLevelByIdOrElseThrow(request.employeeLevelId());
        if (!isEmployeeSubLevelExist(request)) {
            EmployeeSubLevel employeeSubLevel = EmployeeSubLevelMapper.toEntity(request, employeeLevel);
            employeeSubLevelRepository.save(employeeSubLevel);
        } else {
            throw new EmployeeSubLevelException("Employee sub-level already exists");
        }
    }

    public EmployeeSubLevelResponse updateEmployeeSubLevel(Integer id, EmployeeSubLevelRequest request) {
        EmployeeLevel employeeLevel = employeeLevelService.getEmployeeLevelByIdOrElseThrow(request.employeeLevelId());
        if (isEmployeeSubLevelExist(id)) {
            EmployeeSubLevel employeeSubLevel = employeeSubLevelRepository.findById(id)
                    .orElseThrow(() -> new EmployeeSubLevelException("Employee sub-level not found with ID: " + id));

            if (!isEmployeeSubLevelExist(request, employeeSubLevel)) {
                EmployeeSubLevel updatedSubLevel = EmployeeSubLevelMapper.toEntity(request, employeeLevel);
                updatedSubLevel.setId(employeeSubLevel.getId()); // Keep the existing ID
                employeeSubLevelRepository.save(updatedSubLevel);
                return EmployeeSubLevelMapper.toResponse(updatedSubLevel);
            } else {
                throw new EmployeeSubLevelException("Employee sub-level already exists with this name");
            }
        } else {
            throw new EmployeeSubLevelException("Employee sub-level does not exist with this ID");
        }
    }

    public void deleteEmployeeSubLevel(Integer id) {
        if (isEmployeeSubLevelExist(id)) {
            employeeSubLevelRepository.deleteById(id);
        } else {
            throw new EmployeeSubLevelException("Employee sub-level not found");
        }
    }
}
