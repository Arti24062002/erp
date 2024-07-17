package com.erp.erp.moduleAndSubmodule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ModuleAllExcepionHandler {
    @ExceptionHandler(ModuleException.class)
      public ResponseEntity<String> validationError(ModuleException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
