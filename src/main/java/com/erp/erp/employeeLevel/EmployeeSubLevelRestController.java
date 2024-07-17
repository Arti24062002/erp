package com.erp.erp.employeeLevel;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class EmployeeSubLevelRestController {

    private final EmployeeSubLevelService employeeSubLevelService;

    @GetMapping
    public ResponseEntity<List<EmployeeSubLevelResponse>> getAllEmployeeSubLevels() {
        return ResponseEntity.ok(employeeSubLevelService.getAllEmployeeSubLevels());
    }

    @GetMapping("/employee-sub-level/level/{employeeLevelId}")
    public ResponseEntity<List<EmployeeSubLevelResponse>> getEmployeeSubLevelsByEmployeeLevelId(@PathVariable Integer employeeLevelId) {
        return ResponseEntity.ok(employeeSubLevelService.getEmployeeSubLevelsByEmployeeLevelId(employeeLevelId));
    }

    @GetMapping("employee-sub-level/{id}")
    public ResponseEntity<EmployeeSubLevelResponse> getEmployeeSubLevelById(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeSubLevelService.getEmployeeSubLevelById(id));
    }

    @PostMapping("/add-employee-sub-level")
    public ResponseEntity<Void> addEmployeeSubLevel(@RequestBody EmployeeSubLevelRequest request) {
        employeeSubLevelService.addEmployeeSubLevel(request);
        return ResponseEntity.ok().build(); 
    }

    @PutMapping("/update-employee-sub-level/{id}")
    public ResponseEntity<EmployeeSubLevelResponse> updateEmployeeSubLevel(
            @PathVariable Integer id, 
            @RequestBody EmployeeSubLevelRequest request) {
        return ResponseEntity.ok(employeeSubLevelService.updateEmployeeSubLevel(id, request));
    }

    @DeleteMapping("/delete-employee-sub-level/{id}")
    public ResponseEntity<Void> deleteEmployeeSubLevel(@PathVariable Integer id) {
        employeeSubLevelService.deleteEmployeeSubLevel(id);
        return ResponseEntity.ok().build(); 
    }
}
