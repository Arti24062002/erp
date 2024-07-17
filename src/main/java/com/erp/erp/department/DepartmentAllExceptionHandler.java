package com.erp.erp.department;


import java.util.HashMap;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DepartmentAllExceptionHandler {
  
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String,String>> validationError(MethodArgumentNotValidException ex){
        HashMap<String,String> map2=new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        bindingResult.getAllErrors().forEach(e->{
            String fieldError=((FieldError)(e)).getField();
            map2.put(fieldError.toString(), e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(map2);
    }
    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<String> validationError(DepartmentException ex){
     
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
