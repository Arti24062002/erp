package com.erp.erp.role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.erp.erp.permission.PermissionException;

@ControllerAdvice
public class RoleAndPermssionAllExceptionHandler {
      @ExceptionHandler(RoleException.class)
    public ResponseEntity<String> validationError(RoleException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<String> requestBodyNotFoundError(PermissionException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

