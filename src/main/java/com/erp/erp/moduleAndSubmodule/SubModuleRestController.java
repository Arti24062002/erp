package com.erp.erp.moduleAndSubmodule;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class SubModuleRestController {
    private final SubModuleService subModuleService;

    @GetMapping("/submodules")
    public ResponseEntity<List<SubModuleResponse>> getAllSubModules() {
        return ResponseEntity.status(HttpStatus.OK).body(subModuleService.getAllSubModules());
    }

    @GetMapping("/submodules/{id}")
    public ResponseEntity<SubModuleResponse> getSubModuleById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(subModuleService.getSubModuleById(id));
    }
    @GetMapping("/submodules-by-moduleId")
    public ResponseEntity<List<SubModuleResponse>> getSubModuleByModuleId(@RequestParam("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(subModuleService.getSubModuleByModuleId(id));
    }


    @PostMapping("/add-submodule")
    public ResponseEntity<Void> addSubModule(@Valid @RequestBody SubModuleRequest subModuleRequest) {
        subModuleService.addSubModule(subModuleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-submodule/{id}")
    public ResponseEntity<SubModuleResponse> updateSubModule(@PathVariable Integer id,
                                                             @Valid @RequestBody SubModuleRequest subModuleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(subModuleService.updateSubModule(id, subModuleRequest));
    }

    @DeleteMapping("/delete-submodule/{id}")
    public ResponseEntity<Void> deleteSubModule(@PathVariable Integer id) {
        subModuleService.deleteSubModule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
