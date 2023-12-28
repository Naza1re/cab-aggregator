package com.modsen.passengerservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class ValidateException extends Throwable {
    private Map<String,String> errors;
    public ValidateException(Map<String,String> errors){
        this.errors=errors;
    }
}
