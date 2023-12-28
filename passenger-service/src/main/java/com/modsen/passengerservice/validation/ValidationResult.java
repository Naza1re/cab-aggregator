package com.modsen.passengerservice.validation;

import java.util.Map;


public class ValidationResult {
    private  Map<String, String> errors;

    public ValidationResult(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
