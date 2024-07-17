package com.erp.erp.department;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class DepartmentRestController {
   private final DepartmentService departmentService;
    @GetMapping("/departments")

    public ResponseEntity<List<DepartmentResponse>> getDepartments() {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getAllDepartment());
    }
    @GetMapping("/departments/{id}")

    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));
    }
    @PostMapping("/add-department")
    public ResponseEntity<Void> addDepartment( @Valid @RequestBody DepartmentRequest departementRequest) {
       departmentService.addDepartment(departementRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @PutMapping("/update-department/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentRequest departementRequest) {
         
      return  ResponseEntity.accepted().body(departmentService.updateDepartment(id,departementRequest));
       
    }
    @DeleteMapping("/delete-department/{id}")
    public ResponseEntity<DepartmentResponse> deleteDepartment(@PathVariable Integer id) {
         
      departmentService.deleteDepartment(id);
      return ResponseEntity.accepted().build();
       
    }
    
    
}
