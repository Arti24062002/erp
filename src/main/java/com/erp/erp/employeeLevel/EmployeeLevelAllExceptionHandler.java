package com.erp.erp.employeeLevel;



import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class EmployeeLevelAllExceptionHandler {
   
    @ExceptionHandler(EmployeeLevelException.class)
    public ResponseEntity<String> validationError(EmployeeLevelException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
      
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validationError(MethodArgumentNotValidException ex){
        Map<String,String> map=new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getAllErrors().forEach(e->{
            String fieldError=((FieldError)(e)).getField();
            map.put(fieldError, e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(map);
    }
}
