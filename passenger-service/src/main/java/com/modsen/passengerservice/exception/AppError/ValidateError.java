package com.modsen.passengerservice.exception.AppError;

import com.modsen.passengerservice.exception.ValidateException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ValidateError {
    private Map<String,String> errors;

    public ValidateError(Map<String,String> errors){
        this.errors=errors;
    }
}
