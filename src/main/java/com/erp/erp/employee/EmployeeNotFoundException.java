package com.erp.erp.employee;

public class EmployeeNotFoundException extends RuntimeException{
    EmployeeNotFoundException(String message){
        super(message);
    }
}
