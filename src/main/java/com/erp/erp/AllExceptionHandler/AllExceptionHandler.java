package com.erp.erp.AllExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.erp.erp.employee.EmployeeException;
import com.erp.erp.employee.EmployeeNotFoundException;
import com.erp.erp.employeeStatus.EmployeeStatus;
import com.erp.erp.employeeStatus.EmployeeStatusException;
import com.erp.erp.shift.ShiftException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(ShiftException.class)
    public ResponseEntity<String> shiftExeption(ShiftException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<String> shiftExeption(EmployeeException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> shiftExeption(EmployeeNotFoundException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> shiftExeption(ConstraintViolationException ex) {
           Map map=new HashMap<String,String>();
           ex.getConstraintViolations().forEach(fn->{
            map.put(fn.getPropertyPath().toString(), fn.getMessage());
           });
           
        return ResponseEntity.badRequest().body(map);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> shiftExeption(DataIntegrityViolationException ex) {

        return ResponseEntity.badRequest().body("Cant delete this there are some child of this present");
    }
    @ExceptionHandler(EmployeeStatusException.class)
    public ResponseEntity<String> employeeStatusExeption(EmployeeStatusException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allException(Exception ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }
  
    
    
  

}