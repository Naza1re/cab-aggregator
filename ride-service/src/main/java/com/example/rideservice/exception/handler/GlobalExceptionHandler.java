package com.example.rideservice.exception.handler;

import com.example.rideservice.exception.AppError.AppError;
import com.example.rideservice.exception.RideAlreadyAcceptedException;
import com.example.rideservice.exception.RideNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<AppError> handleRideNotFoundException(
            RideNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RideAlreadyAcceptedException.class)
    public ResponseEntity<AppError> handleRideAlreadyAcceptedException(
            RideAlreadyAcceptedException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
