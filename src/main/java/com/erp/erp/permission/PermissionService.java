package com.erp.erp.permission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.erp.role.Role;
import com.erp.erp.role.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public List<Permission> getAllPermissions() {
        List<Permission> all = permissionRepository.findAll();
        return all;
    }

    public Permission getPermissionById(Integer id) {
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission != null) {
            return permission;
        }
        throw new PermissionException("Permission not found");
    }

    public List<Permission> getPermissionById(List<Integer> id) {
        List<Permission> permissions = permissionRepository.findAllById(id);
        if (permissions != null && permissions.size() == id.size()) {
            return permissions;
        }
        throw new PermissionException("Permission not found");
    }

    public Permission getPermissionByIdOrElseThrow(Integer id) {
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission != null) {
            return permission;
        }
        throw new PermissionException("Permission not found");
    }

    public Permission getPermissionObjectByPermissionName(String permission) {
        return permissionRepository.findAll().stream().filter(fn -> fn.getAccessPermissionName().equals(permission))
                .findFirst().orElse(null);

    }

    public List<Permission> getPermissionByRoleId(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null)
            throw new PermissionException("Role does not exist with this id");
        return role.getPermission();
    }

    public boolean isPermissionExist(Integer id) {
        return permissionRepository.findById(id).isPresent();
    }

    public boolean isPermissionExist(String permissionName) {
        return permissionRepository.findAll().stream()
                .filter(fn -> fn.getAccessPermissionName().equals(permissionName))
                .findFirst().isPresent();
    }

    public Permission addPermission(String permissionName) {
        if (!isPermissionExist(permissionName)) {
            Permission permission = new Permission();
            permission.setAccessPermissionName(permissionName);
            permission.setUrl("/" + permissionName);
            permissionRepository.save(permission);
            return permission;
        }
        return null;
    }

    public Permission updatePermission(Permission permission, String permissionName) {
        if (!isPermissionExist(permissionName)) {
            permissionName = permissionName.toLowerCase().replace(" ", "-");
            permission.setAccessPermissionName(permissionName);
            permission.setUrl("/" + permissionName);
            permissionRepository.save(permission);
            return permission;
        }

        return null;
    }

}
