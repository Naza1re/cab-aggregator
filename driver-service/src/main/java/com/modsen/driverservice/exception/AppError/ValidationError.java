package com.modsen.driverservice.exception.AppError;

import com.modsen.driverservice.exception.ValidationException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationError {

    private Map<String,String> errors;

    public ValidationError(Map<String,String> errors){
        this.errors=errors;
    }
}
