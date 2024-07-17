package com.erp.erp.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.erp.erp.department.Department;
import com.erp.erp.department.DepartmentService;
import com.erp.erp.designation.Designation;
import com.erp.erp.designation.DesignationService;
import com.erp.erp.employeeLevel.EmployeeSubLevel;
import com.erp.erp.employeeLevel.EmployeeSubLevelService;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.employeeStatus.EmployeeStatusService;
import com.erp.erp.permission.Permission;
import com.erp.erp.permission.PermissionService;
import com.erp.erp.role.Role;
import com.erp.erp.role.RoleService;
import com.erp.erp.shift.Shift;
import com.erp.erp.shift.ShiftService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

        private final EmployeeRepoistory employeeRepoistory;
        private final EmployeeMapper employeeMapper;
        private final RoleService roleService;
        private final PermissionService permissionService;
        private final EmployeeStatusService employeeStatusService;
        private final EmployeeSubLevelService employeeSubLevelService;
        private final DesignationService designationService;
        private final DepartmentService departmentSercice;
        private final ShiftService shiftService;
        private final Validator validator;

        @Transactional
        public void addEmployee(EmployeeRequest request) {
                validateRequest(request); // Implement validation logic as needed
                if (isDoesNotExist(request)) {
                        System.out.println(request);
                        Role role = roleService.getRoleById(request.getRoleId());
                        List<Permission> permissions = request.getPermission().stream()
                                        .map(permissionService::getPermissionById)
                                        .collect(Collectors.toList());
                        EmployeeStatus employeeStatus = employeeStatusService
                                        .getEmployeeStatusByIdOrElseThrowException(request.getEmployeeStatusId());
                        EmployeeStatus currentStatus = employeeStatusService
                                        .getEmployeeStatusByIdOrElseThrowException(request.getCurrentStatusId());
                        EmployeeSubLevel employeeSubLevel = employeeSubLevelService
                                        .getEmployeeSubLevelByIdOrElseThrowException(request.getEmployeeSubLevelId());
                        Designation designation = designationService
                                        .getDesignationByIdOrElseThrowException(request.getDesignationId());
                        Shift shift = shiftService.getShiftExistByIdOrElseThrowException(request.getShiftId());
                        Department department = departmentSercice
                                        .getDepartmentByIdOrElseThrowException(request.getDepartmentId());
                        Employee employee = employeeMapper.mapToEmployee(request, role, permissions, employeeStatus,
                                        currentStatus,
                                        employeeSubLevel,
                                        designation, department, shift);

                        employeeRepoistory.save(employee);
                        return;
                }

                throw new EmployeeException("Some Exception Occured");

        }

        @Transactional
        public EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest request) {
                validateRequest(request); // Implement validation logic as needed
                Employee existingEmployee = employeeRepoistory.findById(employeeId)
                                .orElseThrow(() -> new EmployeeException("EMployee does not exist"));
                if (checkIfEmployeeExists(request, existingEmployee)) {

                        Role role = roleService.getRoleById(request.getRoleId());
                        List<Permission> permissions = request.getPermission().stream()
                                        .map(permissionService::getPermissionById)
                                        .collect(Collectors.toList());
                        EmployeeStatus employeeStatus = employeeStatusService
                                        .getEmployeeStatusByIdOrElseThrowException(request.getEmployeeStatusId());
                        EmployeeStatus currentStatus = employeeStatusService
                                        .getEmployeeStatusByIdOrElseThrowException(request.getCurrentStatusId());
                        EmployeeSubLevel employeeLevel = employeeSubLevelService
                                        .getEmployeeSubLevelByIdOrElseThrowException(request.getEmployeeSubLevelId());

                        Designation designation = designationService
                                        .getDesignationByIdOrElseThrowException(request.getDesignationId());
                        Shift shift = shiftService.getShiftExistByIdOrElseThrowException(request.getShiftId());
                        Department department = departmentSercice
                                        .getDepartmentByIdOrElseThrowException(request.getDepartmentId());
                        Employee updatedEmployee = employeeMapper.mapFromExistingEmployeeToEmployee(existingEmployee,
                                        request, role, permissions,
                                        employeeStatus, currentStatus, employeeLevel, designation, department, shift);

                        return EmployeeMapper.mapToEmployeeResponse(updatedEmployee);
                }
                throw new EmployeeException("Some exception occured");
        }

        @Transactional(readOnly = true)
        public EmployeeResponse getEmployeeResponseById(Integer employeeId) {
                Employee employee = employeeRepoistory.findById(employeeId)
                                .orElseThrow(() -> new EmployeeNotFoundException(
                                                "Employee not found with id: " + employeeId));

                return EmployeeMapper.mapToEmployeeResponse(employee);
        }

        @Transactional(readOnly = true)
        public Employee getEmployeeByIdOrElseThrowException(Integer employeeId) {
                Employee employee = employeeRepoistory.findById(employeeId)
                                .orElseThrow(() -> new EmployeeNotFoundException(
                                                "Employee not found with id: " + employeeId));

                return (employee);
        }

        @Transactional
        public void deleteEmployee(Integer employeeId) {
                Employee employee = employeeRepoistory.findById(employeeId)
                                .orElseThrow(() -> new EmployeeNotFoundException(
                                                "Employee not found with id: " + employeeId));

                employeeRepoistory.delete(employee);
        }

        public List<EmployeeResponse> getAllEmployees() {
                List<Employee> employees = employeeRepoistory.findAll();
                return employees.stream()
                                .map(EmployeeMapper::mapToEmployeeResponse)
                                .toList();
        }

        private void validateRequest(EmployeeRequest request) {
                var violations = validator.validate(request);
                if (!violations.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (ConstraintViolation<?> violation : violations) {
                                sb.append(violation.getMessage()).append("\n");
                        }
                        throw new ConstraintViolationException("Validation errors occurred: \n" + sb, violations);
                }

        }

        private boolean isDoesNotExist(EmployeeRequest request) {

                if (employeeRepoistory.findByProfessionalEmail(request.getProfessionalEmail()).isPresent()) {
                        throw new EmployeeException("Email already exist");
                }
                if (employeeRepoistory.findByPersonalEmail(request.getPersonalEmail()).isPresent()) {
                        throw new EmployeeException("Personal Email already exist");
                }
                if (employeeRepoistory.findByPhone(request.getPhone()).isPresent()) {
                        throw new EmployeeException("Phone already exist ");
                }
                return true;

        }

        private boolean checkIfEmployeeExists(EmployeeRequest employeeRequest, Employee existingEmployee) {
                Optional<Employee> existingByEmail = employeeRepoistory
                                .findByProfessionalEmail(employeeRequest.getProfessionalEmail());
                if (existingByEmail.isPresent()
                                && !existingByEmail.get().getUserId().equals(existingEmployee.getUserId())) {
                        throw new EmployeeException("Email already exists");
                }
                Optional<Employee> existingByPEmail = employeeRepoistory
                                .findByPersonalEmail(employeeRequest.getPersonalEmail());
                if (existingByPEmail.isPresent()
                                && !existingByPEmail.get().getUserId().equals(existingEmployee.getUserId())) {
                        throw new EmployeeException("Personal Email already exists");
                }
                Optional<Employee> existingByPPhoneNumbr = employeeRepoistory.findByPhone(employeeRequest.getPhone());
                if (existingByPPhoneNumbr.isPresent()
                                && !existingByPPhoneNumbr.get().getUserId().equals(existingEmployee.getUserId())) {
                        throw new EmployeeException("Phone number already exists");
                }
                return true;
        }

        public List<Employee> getEmployeeByRole(String role) {
                return employeeRepoistory.findAll().stream().filter(f -> f.getRole().getRole().equals(role))
                                .collect(Collectors.toList());
        }

        public List<EmployeeResponse> findByUserIdContaining(String partialUserId) {
                List<Employee> employees = employeeRepoistory.findAll();

                return employees.stream()
                                .filter(employee -> String.valueOf(employee.getUserId()).contains(partialUserId))
                                .map(EmployeeMapper::mapToEmployeeResponse).collect(Collectors.toList());
        }

        public List<EmployeeResponse> filterEmployees(String userId, String name, Integer departmentId) {
                // Convert empty strings to null for proper handling in repository method
                if (userId != null && userId.isEmpty()) {
                        userId = null;
                }
                if (name != null && name.isEmpty()) {
                        name = null;
                }
                if (departmentId != null && departmentId.toString().equals("")) {
                        name = null;
                }

                // Call repository method with filtered parameters

                return employeeRepoistory.findByFilters(userId, name, departmentId).stream()
                                .map(EmployeeMapper::mapToEmployeeResponse).toList();
        }
}
