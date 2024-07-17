package com.erp.erp.designation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erp.erp.department.Department;
import com.erp.erp.department.DepartmentException;
import com.erp.erp.department.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DesignationService {
    private final DesignationRepoistory designationRepoistory;
    private final DepartmentService departmentService;

    public List<DesignationResponse> getAlldesignation() {
        List<Designation> all = designationRepoistory.findAll();
        return all.stream().map(ds -> DesignationMapper.designationToDesignationResponse(ds))
                .collect(Collectors.toList());

    }
    public List<DesignationResponse> getDesignationByDepartmentId(Integer id) {
        Department orElse = departmentService.getDepartmentByIdOrElseThrowException(id);
        if (orElse != null) {
            return orElse.getDesignation().stream().map(DesignationMapper::designationToDesignationResponse).toList();
        }
        throw new DepartmentException("Department is not exist with this "+id);
    }
    public DesignationResponse getDesignationById(Integer id) {
        Designation orElse = designationRepoistory.findById(id).orElse(null);
        if (orElse != null) {
            return DesignationMapper.designationToDesignationResponse(orElse);
        }
        throw new DesignationException("designation is not exist");
    }
    public Designation getDesignationByIdOrElseThrowException(Integer id) {
        Designation orElse = designationRepoistory.findById(id).orElse(null);
        if (orElse != null) {
            return orElse;
        }
        throw new DesignationException("designation is not exist");
    }
    public boolean isDesignationExist(Integer dId) {
        return designationRepoistory.findById(dId).isPresent();
    }

    public boolean isDesignationExist(DesignationRequest designationRequest) {
        return designationRepoistory.findAll().stream()
                .filter(designation -> designation.designationName.equals(designationRequest.designationName))
                .findFirst()
                .isPresent();
    }
    public boolean isDesignationExist(DesignationRequest designationRequest,Designation existDesignation) {
        return designationRepoistory.findAll().stream()
                .filter(designation -> designation.designationName.equals(designationRequest.designationName)&& !(designation.getId()==existDesignation.getId())) 
                .findFirst()
                .isPresent();
    }

    public void addDesignation(DesignationRequest designationRequest) {
        Department department=departmentService.getDepartmentByIdOrElseThrowException(designationRequest.getDepartmentId());
        if (!isDesignationExist(designationRequest)) {
            Designation fromdesignationRequest = DesignationMapper.fromDesignationRequest(designationRequest,department);
            designationRepoistory.save(fromdesignationRequest);
        } else {
            throw new DesignationException("designation already Exist");
        }
    }

    public DesignationResponse updateDesignation(Integer id,
            DesignationRequest designationRequest) {
                Department department=departmentService.getDepartmentByIdOrElseThrowException(designationRequest.getDepartmentId());
        if (isDesignationExist(id)) {
            Designation designation = designationRepoistory.findById(id).orElse(null);
      
           if(!isDesignationExist(designationRequest,designation)){
            var result = DesignationMapper.designationRequestToDesignation(designation, designationRequest,department);
            designationRepoistory.save(result);
            return DesignationMapper.designationToDesignationResponse(result);
           }
           else{
            throw new DesignationException("designation already Exist by this name");
           }
          
           

        }
        throw new DesignationException("designation does not Exist by this id");
    }

    public void deleteDesignation(Integer id) {
        if (isDesignationExist(id))
            designationRepoistory.deleteById(id);
        else
            throw new DesignationException("designation Not found ");
    }
}
