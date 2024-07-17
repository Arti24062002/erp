package com.erp.erp.employeeLevel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class EmployeeLevelRestController {
    private final EmployeeLevelService employeeLevelService;

    @GetMapping("/employee-levels")
    public ResponseEntity<List<EmployeeLevelResponse>> getAllEmployeeLevels() {
        return ResponseEntity.status(HttpStatus.FOUND).body(employeeLevelService.getAllEmployeeLevels());
    }

    @GetMapping("/employee-levels/{id}")
    public ResponseEntity<EmployeeLevelResponse> getEmployeeLevelById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(employeeLevelService.getEmployeeLevelById(id));
    }

    @PostMapping("/add-employee-level")
    public ResponseEntity<Void> addEmployeeLevel(@Valid @RequestBody EmployeeLevelRequest employeeLevelRequest) {
        employeeLevelService.addEmployeeLevel(employeeLevelRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-employee-level/{id}")
    public ResponseEntity<EmployeeLevelResponse> updateEmployeeLevel(@PathVariable Integer id,
            @Valid @RequestBody EmployeeLevelRequest employeeLevelRequest) {
        return ResponseEntity.ok().body(employeeLevelService.updateEmployeeLevel(id, employeeLevelRequest));
    }

    @DeleteMapping("/delete-employee-level/{id}")
    public ResponseEntity<Void> deleteEmployeeLevel(@PathVariable Integer id) {
        employeeLevelService.deleteEmployeeLevel(id);
        return ResponseEntity.accepted().build();
    }
}
