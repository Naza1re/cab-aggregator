package com.modsen.driverservice.exception.handler;

import com.modsen.driverservice.exception.AppError.AppError;
import com.modsen.driverservice.exception.AppError.ValidationError;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalHandler {


    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<AppError> handleDriverNotFoundException(
            DriverNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationError> handleValidationError(
            ValidationException ex) {
        Map<String,String> errorMessage = ex.getErrors();
        return new ResponseEntity<>(new ValidationError(errorMessage),HttpStatus.BAD_REQUEST);
    }
}
