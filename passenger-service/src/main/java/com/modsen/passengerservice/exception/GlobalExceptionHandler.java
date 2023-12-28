package com.modsen.passengerservice.exception;

import com.modsen.passengerservice.exception.AppError.AppError;
import com.modsen.passengerservice.exception.AppError.ValidateError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<AppError> handlePassengerNotFoundException(
            PassengerNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ValidateError> handleValidateException(ValidateException ex){
        Map<String ,String> errors = ex.getErrors();
        return new ResponseEntity<>(new ValidateError(errors),HttpStatus.BAD_REQUEST);
    }
}

