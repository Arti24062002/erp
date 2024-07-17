package com.erp.erp.department;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepoistroy departmentRepoistroy;

    public List<DepartmentResponse> getAllDepartment() {
        List<Department> all = departmentRepoistroy.findAll();
        return all.stream().map(ds -> DepartmentMapper.departmentToDepartmentResponse(ds)).collect(Collectors.toList());

    }

    public DepartmentResponse getDepartmentById(Integer id) {
        Department orElse = departmentRepoistroy.findById(id).orElse(null);
        if (orElse != null) {
            return DepartmentMapper.departmentToDepartmentResponse(orElse);
        }
        throw new DepartmentException("Department is not exist");
    }
    
    public Department getDepartmentByIdOrElseThrowException(Integer id) {
        Department orElse = departmentRepoistroy.findById(id).orElse(null);
        if (orElse != null) {
            return orElse;
        }
        throw new DepartmentException("Department is not exist with this Id");
    }

    public boolean isDepartmentExist(Integer dId) {
        return departmentRepoistroy.findById(dId).isPresent();
    }

    public boolean isDepartmentExist(DepartmentRequest departementRequest) {
        return departmentRepoistroy.findAll().stream()
                .filter(department -> department.dpartmentName.equals(departementRequest.getDepartmentName())).findFirst()
                .isPresent();
    }

    public void addDepartment(DepartmentRequest departementRequest) {
        if (!isDepartmentExist(departementRequest)) {
            Department fromDepartmentRequest = DepartmentMapper.fromDepartmentRequest(departementRequest);
            departmentRepoistroy.save(fromDepartmentRequest);
        } else {
            throw new DepartmentException("Department already Exist");
        }
    }

    public DepartmentResponse updateDepartment(Integer id,
            DepartmentRequest departementRequest) {
        if (isDepartmentExist(id)) {
            Department department = departmentRepoistroy.findById(id).orElse(null);

            if (!isDepartmentExist(departementRequest,department)) {
                var result = DepartmentMapper.departmentRequestToDepartment(department, departementRequest);
                departmentRepoistroy.save(result);
                return DepartmentMapper.departmentToDepartmentResponse(result);
            } else
                throw new DepartmentException("Department  alredy Exist by this name");

        }
        throw new DepartmentException("Department does not Exist by this id");
    }

    public void deleteDepartment(Integer id) {
        if (isDepartmentExist(id))
            departmentRepoistroy.deleteById(id);
        else
            throw new DepartmentException("Department Not found ");
    }
    public boolean isDepartmentExist(DepartmentRequest departementRequest,Department exisDepartment) {
        return departmentRepoistroy.findAll().stream()
                .filter(department -> department.dpartmentName.equals(departementRequest.getDepartmentName())&& !(department.getId()==exisDepartment.getId())).findFirst()
                .isPresent();
    }
    
}
