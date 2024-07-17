package com.erp.erp.permissionForUser;

import com.erp.erp.employee.Employee;
import com.erp.erp.employee.EmployeeException;
import com.erp.erp.employee.EmployeeRepoistory;
import com.erp.erp.employee.EmployeeService;
import com.erp.erp.permission.Permission;
import com.erp.erp.permission.PermissionException;
import com.erp.erp.permission.PermissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPermissionService {

    private final EmployeeRepoistory userRepository;
    private final PermissionRepository permissionRepository;
    private final EmployeeService employeeService;

    public boolean assignPermissionToUser(UserPermissionRequest userPermissionRequest) {
        int userId = userPermissionRequest.userId();
        List<Integer> permissionIds = userPermissionRequest.checkedPermissionIds();
        Employee user = employeeService.getEmployeeByIdOrElseThrowException(userId);
        if (user != null) {
            List<Permission> allById = permissionRepository.findAllById(permissionIds);

            if (allById.size() == permissionIds.size()) {
                allById.stream().forEach(ab -> {
                    if (!user.getPermission().contains(ab)) {
                        user.getPermission().add(ab);
                    }
                });
                userRepository.save(user);
                return true;
            }
            throw new PermissionException("Permission not exist");
        }
        throw new EmployeeException("User does not exist");
    }

    public boolean unAssignPermissionToUser(UserPermissionRequest userPermissionRequest) {
        int userId = userPermissionRequest.userId();
        List<Integer> permissionIds = userPermissionRequest.unCheckedPermissionIds();
        Employee user = employeeService.getEmployeeByIdOrElseThrowException(userId);
        if (user != null) {

            List<Permission> allById = permissionRepository.findAllById(permissionIds);
            if (allById.size() == permissionIds.size()) {
                user.getPermission().removeAll(allById);
                userRepository.save(user);
                return true;
            } else
                throw new PermissionException("All Permission with Id does not exist");

        }
        return false;
    }

    public List<Permission> getAllPermissionsForUser(int userId) {
        Employee employee = employeeService.getEmployeeByIdOrElseThrowException(userId);
        return employee.getPermission();
    }

    public boolean assignAndunAssignPermissionToUser(UserPermissionRequest userPermissionRequest) {
        if (assignPermissionToUser(userPermissionRequest) && unAssignPermissionToUser(userPermissionRequest))
            return true;
        return false;
    }

}
