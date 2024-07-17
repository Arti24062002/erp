package com.erp.erp.role;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleResponse> getAllRoles() {
        List<Role> all = roleRepository.findAll();
        return all.stream()
                .map(RoleModelMapper::roleToRoleResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getRoleResponseById(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return RoleModelMapper.roleToRoleResponse(role);
        }
        throw new RoleException("Role not found");
    }

    public Role getRoleById(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return role;
        }
        throw new RoleException("Role not found");
    }

    public Role getRoleByIdOrElseThrowException(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role != null) {
            return role;
        }
        throw new RoleException("Role not found");
    }

    public boolean isRoleExist(Integer id) {
        return roleRepository.findById(id).isPresent();
    }

    public boolean isRoleExist(RoleRequest id) {
        return roleRepository.findAll().stream().filter(fn -> fn.getRole().equalsIgnoreCase(id.role())).findFirst().isPresent();
    }
    public boolean isRoleExist(RoleRequest id,Role exis) {
        return roleRepository.findAll().stream().filter(fn -> fn.getRole().equalsIgnoreCase(id.role())&& fn.getId()!= exis.getId()).findFirst().isPresent();
    }


    public void addRole(RoleRequest roleRequest) {
        System.out.println(roleRequest);
        if (!isRoleExist(roleRequest)) {
            Role role = RoleModelMapper.fromRoleRequest(roleRequest);
            roleRepository.save(role);
        } else
            throw new RoleException("Role already exist with this name");

    }

    public RoleResponse updateRole(Integer id, RoleRequest roleRequest) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            if (!isRoleExist(roleRequest,existingRole)) {
                existingRole.setRole(roleRequest.role());
                roleRepository.save(existingRole);
            } else {
                throw new RoleException("Role alredy exist");
            }

            return RoleModelMapper.roleToRoleResponse(existingRole);
        }
        throw new RoleException("Role not found");
    }

    public void deleteRole(Integer id) {
        if (isRoleExist(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleException("Role not found");
        }
    }
}
