package com.erp.erp.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepoistory extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByPersonalEmail(String personalEmail);
    Optional<Employee> findByProfessionalEmail(String personalEmail);
    Optional<Employee> findByPhone(Long phone);
    @Query("SELECT e FROM Employee e " +
           "WHERE (:userId is null or cast(e.userId as string) like %:userId%) " +
           "AND (:name is null or lower(e.firstName) like %:name% or lower(e.lastName) like %:name%) " +
           "AND (:departmentId is null or e.department.id = :departmentId)")
    List<Employee> findByFilters(
            @Param("userId") String userId,
            @Param("name") String name,
            @Param("departmentId") Integer departmentId);
}
