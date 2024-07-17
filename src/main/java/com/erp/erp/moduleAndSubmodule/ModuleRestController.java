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
public class ModuleRestController {
    private final ModuleService moduleService;

    @GetMapping("/modules")
    public ResponseEntity<List<ModuleResponse>> getAllModules() {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.getAllModules());
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.getModuleById(id));
    }

    @PostMapping("/add-module")
    public ResponseEntity<Void> addModule(@Valid @RequestBody ModuleRequest moduleRequest) {
        moduleService.addModule(moduleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-module/{id}")
    public ResponseEntity<ModuleResponse> updateModule(@PathVariable Integer id,
                                                       @Valid @RequestBody ModuleRequest moduleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.updateModule(id, moduleRequest));
    }

    @DeleteMapping("/delete-module/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) {
        moduleService.deleteModule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
