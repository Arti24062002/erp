package com.erp.erp.designation;

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
public class DesignationRestController {
    private final DesignationService designationService;

    @GetMapping("/designations")

    public ResponseEntity<List<DesignationResponse>> getDesignation() {
        return ResponseEntity.status(HttpStatus.OK).body(designationService.getAlldesignation());
    }

    @GetMapping("/designations/{id}")

    public ResponseEntity<DesignationResponse> getDesignationById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(designationService.getDesignationById(id));
    }

    @PostMapping("/add-designation")
    public ResponseEntity<Void> addDesignation(@Valid @RequestBody DesignationRequest designationRequest) {
        designationService.addDesignation(designationRequest);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/update-designation/{id}")
    public ResponseEntity<DesignationResponse> updateDesignation(@PathVariable Integer id,
            @Valid @RequestBody DesignationRequest designationRequest) {

        return ResponseEntity.accepted().body(designationService.updateDesignation(id, designationRequest));

    }

    @DeleteMapping("/delete-designation/{id}")
    public ResponseEntity<DesignationResponse> deleteDesignation(@PathVariable Integer id) {

        designationService.deleteDesignation(id);
        return ResponseEntity.accepted().build();

    }
    @GetMapping("/designations-by-department/{id}")

    public ResponseEntity<List<DesignationResponse>> getDesignationByDepartment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(designationService.getDesignationByDepartmentId(id));
    }

}
