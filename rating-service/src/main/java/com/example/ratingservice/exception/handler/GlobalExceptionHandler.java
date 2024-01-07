package com.example.ratingservice.exception.handler;

import com.example.ratingservice.exception.*;
import com.example.ratingservice.exception.AppError.AppError;
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

    @ExceptionHandler(DriverAlreadyExistException.class)
    public ResponseEntity<AppError> handleDriverAlreadyExistException(
            DriverAlreadyExistException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DriverRatingNotFoundException.class)
    public ResponseEntity<AppError> handleDriverRatingNotFoundException(
            DriverRatingNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PassengelAlreadyExistException.class)
    public ResponseEntity<AppError> handlePassengerAlreadyExistException(
            PassengelAlreadyExistException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PassengerRatingNotFoundException.class)
    public ResponseEntity<AppError> handlePassengerRatingNotFoundException(
            PassengerRatingNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<AppError> handleIncorrectDriverIdException(
            IncorrectIdException ex){
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
