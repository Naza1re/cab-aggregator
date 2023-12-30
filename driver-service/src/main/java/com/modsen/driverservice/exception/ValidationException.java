package com.modsen.driverservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ValidationException extends Throwable {

    private Map<String,String> errors;
    public ValidationException(Map<String,String> errors){
        this.errors=errors;
    }
}
