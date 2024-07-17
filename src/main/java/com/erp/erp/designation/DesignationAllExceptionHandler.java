package com.erp.erp.designation;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DesignationAllExceptionHandler {
     @ExceptionHandler(DesignationException.class)
    public ResponseEntity<String> validationError(DesignationException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> requestBodyNotFoundError(HttpMessageNotReadableException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
