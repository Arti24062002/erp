package com.erp.erp.role;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class RoleRestController {
    private final RoleService roleService;
    TemplateEngine templateEngine;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleResponseById(id));
    }

    @PostMapping("/add-role")
    public ResponseEntity<Void> addRole(@Valid @RequestBody RoleRequest roleRequest) {
        roleService.addRole(roleRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable Integer id,
                                                   @Valid @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleRequest));
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
   
}
