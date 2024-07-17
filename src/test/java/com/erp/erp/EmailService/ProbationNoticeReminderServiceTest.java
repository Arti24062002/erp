package com.erp.erp.EmailService;

import com.erp.erp.EmailServiceAndScheduling.EmailService;
import com.erp.erp.EmailServiceAndScheduling.ProbationNoticeReminderService;
import com.erp.erp.employee.Employee;
import com.erp.erp.employee.EmployeeRepoistory;
import com.erp.erp.employeeStatus.EmployeeStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProbationNoticeReminderServiceTest {

    @Mock
    private EmployeeRepoistory employeeRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ProbationNoticeReminderService probationNoticeReminderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendProbationNoticeReminders() {
        // Mock current date 7 days from now
        Calendar calendar = Calendar.getInstance();
        // calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date targetDate = calendar.getTime();

        // Mock list of employees
        List<Employee> mockEmployees = new ArrayList<>();
        // Example: employee with probation ending exactly 7 days from now
        Employee employee = new Employee();
        employee.setUserId(1);
        employee.setDoj(new java.sql.Date(targetDate.getTime()));
        employee.setCurrentStatus(new EmployeeStatus(1, 7, "Probation", "Description"));
        mockEmployees.add(employee);

        // Stub repository method to return mock data
        when(employeeRepository.findAll()).thenReturn(mockEmployees);

        // Invoke the method to be tested
        probationNoticeReminderService.sendProbationNoticeReminders();
       
        // Verify that emailService.sendReminderEmail was called once for each
        // applicable employee
        
        verify(emailService, times(1)).sendReminderEmail(employee, "probation or notice");
    }
}
