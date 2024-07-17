package com.erp.erp.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class EmployeeRestController {
    private final EmployeeService employeeService;

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Integer id) {
        EmployeeResponse response = employeeService.getEmployeeResponseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> responses = employeeService.getAllEmployees();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/add-employee")
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        System.out.println(employeeRequest);
        employeeService.addEmployee(employeeRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer id,
            @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.updateEmployee(id, employeeRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees-like")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByIdLike(@RequestParam String id) {
        List<EmployeeResponse> byUserIdContaining = employeeService.findByUserIdContaining(id);
        ;
        return ResponseEntity.ok().body(byUserIdContaining);
    }

    @GetMapping("/employees/filter")
    public List<EmployeeResponse> filterEmployees(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer departmentId) {
        return employeeService.filterEmployees(userId, name, departmentId);

    }

}
