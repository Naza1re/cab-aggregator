package com.example.ratingservice.exception.handler;

import com.example.ratingservice.exception.AppError.AppError;
import com.example.ratingservice.exception.DriverAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.exception.PassengelAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
