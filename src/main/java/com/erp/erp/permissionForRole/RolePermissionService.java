package com.erp.erp.permissionForRole;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.erp.permission.Permission;
import com.erp.erp.permission.PermissionException;
import com.erp.erp.permission.PermissionRepository;
import com.erp.erp.permission.PermissionService;
import com.erp.erp.role.Role;
import com.erp.erp.role.RoleException;
import com.erp.erp.role.RoleRepository;
import com.erp.erp.role.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final PermissionRepository permissionRepository;

    public boolean assignPermissionToRole(RolePermissionRequest rolePermissionRequest) {
        int roleID = rolePermissionRequest.roleId();
        List<Integer> perMissionIds = rolePermissionRequest.checkedPermissionIds();
        System.out.println(perMissionIds);

        if (roleService.isRoleExist(roleID)) {
            Role roleById = roleService.getRoleById(roleID);
            System.out.println(roleById);
            List<Permission> allById = permissionRepository.findAllById(perMissionIds);

            if (allById.size() == (perMissionIds.size())) {
                 
               allById.stream().forEach(f->{
                if(!roleById.getPermission().contains(f))
                    roleById.getPermission().add(f);
               });
                roleRepository.save(roleById);
                return true;
            }
            throw new PermissionException("Permission not exist");
        }
        throw new RoleException("Role does nor exist");

    }

    public boolean unAssignPermissionToRole(RolePermissionRequest rolePermissionRequest) {
        int roleID = rolePermissionRequest.roleId();
        List<Integer> perMissionIds = rolePermissionRequest.unCheckedPermissionIds();
        System.out.println(perMissionIds);
        if (roleService.isRoleExist(roleID)) {
            Role roleById = roleService.getRoleById(roleID);
            List<Permission> allById = permissionRepository.findAllById(perMissionIds);
            if (allById.size() == (perMissionIds.size())) {
                roleById.getPermission().removeAll(allById);

                roleRepository.save(roleById);
                return true;
            }
            return false;
        }
        return false;

    }

    public List<Permission> getAllPermissionForRole(int roleId) {
        return permissionService.getPermissionByRoleId(roleId);

    }

    public boolean assignAndunAssignPermissionToRole(RolePermissionRequest rolePermissionRequest) {
        if (assignPermissionToRole(rolePermissionRequest) && unAssignPermissionToRole(rolePermissionRequest))
            return true;
        return false;
    }

}
