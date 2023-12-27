package com.modsen.passengerservice.exception;

import com.modsen.passengerservice.exception.AppError.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<AppError> passengerNotFoundException(PassengerNotFoundException ex){
        return new ResponseEntity<>(new AppError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
