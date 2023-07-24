package com.payroll.management.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateException extends RuntimeException{

    public InvalidDateException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
