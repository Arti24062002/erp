package com.erp.erp.EmailServiceAndScheduling;

import com.erp.erp.employee.Employee;

import com.erp.erp.employee.EmployeeService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmployeeService  employeeService;

    public void sendReminderEmail(Employee employee, String periodType) {
        String subject = "Reminder: " + periodType + " Period Ending Soon";

        String adminEmail = getAdminEmail();
        List<String> ccEmails = getHREmails();

        try {
            String body = getEmailContent(employee, periodType);
            sendEmail(adminEmail, ccEmails, subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAdminEmail() {
        return employeeService.getEmployeeByRole("ADMIN")
                .stream()
                .map(Employee::getProfessionalEmail)
                .findFirst()
                .orElse("admin@example.com"); // Fallback email
    }

    private List<String> getHREmails() {
        return employeeService.getEmployeeByRole("HR")
                .stream()
                .map(Employee::getProfessionalEmail)
                .collect(Collectors.toList());
    }

    private String getEmailContent(Employee employee, String periodType) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("periodType", periodType);
        model.put("firstName", employee.getFirstName());
        model.put("lastName", employee.getLastName());
        model.put("userId", employee.getUserId());
        model.put("email", employee.getProfessionalEmail());
        model.put("personalEmail", employee.getPersonalEmail());
        model.put("phone", employee.getPhone());
        model.put("role", employee.getRole().getRole());
        model.put("doj", employee.getDoj());
        model.put("designation", employee.getDesignation().getDesignationName());
        model.put("employeeLevel", employee.getEmployeeSubLevel().getSubLevel()+ " "+employee.getEmployeeSubLevel().getSubLevelTitle());
        model.put("gender", employee.getGender());
        model.put("department", employee.getDepartment().getDpartmentName());
        model.put("shift", employee.getShift().getShiftName());
        model.put("currentStatus", employee.getCurrentStatus().getEmployeeStatus());
        model.put("company", employee.getCompany());
        model.put("status", employee.isStatus());
        model.put("rm", employee.getRm());
        model.put("secondRm", employee.getSecondRm());
        Context context = new Context();
        context.setVariables(model);
        // Template template =
        // freemarkerConfig.getTemplate("reminder-email-template.html");
        return templateEngine.process("email-template", context);
    }

    private void sendEmail(String to, List<String> cc, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        InternetAddress[] ccAddresses = cc.stream()
        .map(email -> {
            try {
                return new InternetAddress(email);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })
        .toArray(InternetAddress[]::new);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setCc(ccAddresses);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
