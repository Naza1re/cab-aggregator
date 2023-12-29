package com.modsen.driverservice.exception.handler;

import com.modsen.driverservice.exception.AppError.AppError;
import com.modsen.driverservice.exception.DriverNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {


    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<AppError> handleDriverNotFoundException(
            DriverNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }
}
