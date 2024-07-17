package com.erp.erp.permissionForUser;

import com.erp.erp.permission.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    @PostMapping("/assign-user-permission")
    public ResponseEntity<Boolean> assignPermissionToUser(@RequestBody UserPermissionRequest userPermissionRequest) {
        return ResponseEntity.accepted().body(userPermissionService.assignPermissionToUser(userPermissionRequest));
    }

    @GetMapping("/get-user-permission/{userId}")
    public List<Permission> getAllPermissionsForUser(@NotNull @PathVariable Integer userId) {
        return userPermissionService.getAllPermissionsForUser(userId);
    }

    @PostMapping("/unassign-user-permission")
    public ResponseEntity<Boolean> unAssignPermissionToUser(@RequestBody UserPermissionRequest userPermissionRequest) {
        return ResponseEntity.accepted().body(userPermissionService.unAssignPermissionToUser(userPermissionRequest));
    }
    @PostMapping("/assign-and-unassign-user-permission")
    public ResponseEntity<Boolean> assignAndunAssignPermissionToRole(@RequestBody UserPermissionRequest userPermissionRequest){
       return ResponseEntity.ok().body(userPermissionService.assignAndunAssignPermissionToUser(userPermissionRequest));

    }
}

