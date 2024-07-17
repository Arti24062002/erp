package com.erp.erp.permissionForRole;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.erp.erp.permission.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;
    @PostMapping("/assign-role-permission")
    public ResponseEntity<Boolean> assignPermissionToRole(@RequestBody RolePermissionRequest roleRequest){
       return ResponseEntity.accepted().body(rolePermissionService.assignPermissionToRole(roleRequest));

    }
    @GetMapping("/get-role-permission/{roleId}")
    public List<Permission> getAllPermissionForRole(@NotNull @PathVariable Integer roleId){
      return rolePermissionService.getAllPermissionForRole(roleId);

    }
    @PostMapping("/unassign-role-permission")
    public ResponseEntity<Boolean> unAssignPermissionToRole(@RequestBody RolePermissionRequest roleRequest){
       return ResponseEntity.accepted().body(rolePermissionService.unAssignPermissionToRole(roleRequest));

    }
    @PostMapping("/assign-and-unassign-role-permission")
    public ResponseEntity<Boolean> assignAndunAssignPermissionToRole(@RequestBody RolePermissionRequest roleRequest){
       return ResponseEntity.ok().body(rolePermissionService.assignAndunAssignPermissionToRole(roleRequest));

    }
}
