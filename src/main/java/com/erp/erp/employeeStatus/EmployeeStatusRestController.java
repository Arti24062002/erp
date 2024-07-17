package com.erp.erp.employeeStatus;

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
@RequestMapping("/rest")
@RequiredArgsConstructor
public class EmployeeStatusRestController {
    private final EmployeeStatusService employeeStatusService;

    @GetMapping("/employee-statuses")
    public ResponseEntity<List<EmployeeStatusResponse>> getAllEmployeeStatuses() {
        return ResponseEntity.status(HttpStatus.FOUND).body(employeeStatusService.getAllEmployeeStatuses());
    }

    @GetMapping("/employee-statuses/{id}")
    public ResponseEntity<EmployeeStatusResponse> getEmployeeStatusById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(employeeStatusService.getEmployeeStatusById(id));
    }

    @PostMapping("/add-employee-status")
    public ResponseEntity<Void> addEmployeeStatus(@Valid @RequestBody EmployeeStatusRequest employeeStatusRequest) {
        employeeStatusService.addEmployeeStatus(employeeStatusRequest);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/update-employee-status/{id}")
    public ResponseEntity<EmployeeStatusResponse> updateEmployeeStatus(@PathVariable Integer id,
            @Valid @RequestBody EmployeeStatusRequest employeeStatusRequest) {
        return ResponseEntity.accepted().body(employeeStatusService.updateEmployeeStatus(id, employeeStatusRequest));
    }

    @DeleteMapping("/delete-employee-status/{id}")
    public ResponseEntity<Void> deleteEmployeeStatus(@PathVariable Integer id) {
        employeeStatusService.deleteEmployeeStatus(id);
        return ResponseEntity.accepted().build();
    }
}
