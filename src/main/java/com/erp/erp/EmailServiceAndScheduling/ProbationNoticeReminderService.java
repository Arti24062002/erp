package com.erp.erp.EmailServiceAndScheduling;

import com.erp.erp.employee.Employee;
import com.erp.erp.employee.EmployeeRepoistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbationNoticeReminderService {

    @Autowired
    private EmployeeRepoistory employeeRepository;

    @Autowired
    private EmailService emailService;

    @Async("taskExecutor")
    @Scheduled(cron = "0 0 9 * * ?")// Runs daily at 9 AM
    public void sendProbationNoticeReminders() {
        System.out.println("Started Working");
        // Get the current date and the date 7 days from now
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(7);

        // Fetch all employees
        List<Employee> allEmployees = employeeRepository.findAll();

        // Filter employees whose probation or notice period ends in the next 7 days
        List<Employee> employeesEndingProbationOrNotice = allEmployees.stream()
                .filter(employee -> isProbationOrNoticeEndingSoon(employee, targetDate))
                .collect(Collectors.toList());

        // Send email reminders
        for (Employee employee : employeesEndingProbationOrNotice) {
            emailService.sendReminderEmail(employee, "probation or notice");
        }
    }

    private boolean isProbationOrNoticeEndingSoon(Employee employee, LocalDate targetDate) {
        LocalDate dojPlusPeriodEnd = employee.getDoj().toLocalDate()
                .plusDays(employee.getCurrentStatus().getProbationTimeinDays());

        // Compare only the dates, ignoring time
        return dojPlusPeriodEnd.isEqual(targetDate);
    }
}
