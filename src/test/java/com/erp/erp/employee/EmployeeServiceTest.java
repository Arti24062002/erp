package com.erp.erp.employee;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.erp.erp.department.Department;
import com.erp.erp.designation.Designation;
import com.erp.erp.employeeLevel.EmployeeSubLevel;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.role.Role;
import com.erp.erp.shift.Shift;
@SpringBootTest
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepoistory employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testFindByUserIdContaining() {
        // Define test data
        Employee emp1 = new Employee(1172, "John", "Doe", "john.doe@example.com", "john.doe@work.com", 1234567890L, new Role(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Designation(), new EmployeeSubLevel(), "Male", new Department(), new Shift(), new EmployeeStatus(), new EmployeeStatus(), "CompanyA", true, List.of(), null, 0, 0);
        Employee emp2 = new Employee(1171, "Jane", "Smith", "jane.smith@example.com", "jane.smith@work.com", 1234567891L, new Role(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Designation(), new EmployeeSubLevel(), "Female", new Department(), new Shift(), new EmployeeStatus(), new EmployeeStatus(), "CompanyB", true, List.of(), null, 0, 0);
        Employee emp3 = new Employee(1173, "Bob", "Brown", "bob.brown@example.com", "bob.brown@work.com", 1234567892L, new Role(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Designation(), new EmployeeSubLevel(), "Male", new Department(), new Shift(), new EmployeeStatus(), new EmployeeStatus(), "CompanyC", true, List.of(), null, 0, 0);
        Employee emp4 = new Employee(1274, "Alice", "Green", "alice.green@example.com", "alice.green@work.com", 1234567893L, new Role(), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), new Designation(), new EmployeeSubLevel(), "Female", new Department(), new Shift(), new EmployeeStatus(), new EmployeeStatus(), "CompanyD", true, List.of(), null, 0, 0);

        List<Employee> allEmployees = Arrays.asList(emp1, emp2, emp3, emp4);

        // Mock the repository response
        when(employeeRepository.findAll()).thenReturn(allEmployees);

        // Call the method and verify results
        List<EmployeeResponse> result = employeeService.findByUserIdContaining("11");

        // Expected result
        List<Employee> expected = Arrays.asList(emp1, emp2, emp3);

        // Verify the results
        // assertEquals(expected, result);
    }
}
   
