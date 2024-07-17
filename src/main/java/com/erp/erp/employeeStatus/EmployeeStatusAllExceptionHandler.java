package com.erp.erp.employeeStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class EmployeeStatusAllExceptionHandler {
    @ExceptionHandler(EmployeeStatusException.class)
     public ResponseEntity<String> validationError(EmployeeStatusException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
