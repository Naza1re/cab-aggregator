package com.example.rideservice.exception.handler;

import com.example.rideservice.exception.AppError.AppError;
import com.example.rideservice.exception.RideNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<AppError> handleRideNotFoundException(
            RideNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }
}
