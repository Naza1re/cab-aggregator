package com.modsen.driverservice.exception.handler;

import com.modsen.driverservice.exception.AppError.AppError;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.exception.EmailAlreadyExistException;
import com.modsen.driverservice.exception.PhoneAlreadyExistException;
import com.modsen.driverservice.exception.SortTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<AppError> handleDriverNotFoundException(
            DriverNotFoundException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<AppError>  handleEmailAlreadyExistException(
            EmailAlreadyExistException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SortTypeException.class)
    public ResponseEntity<AppError> handleSortTypeException(
            SortTypeException ex){
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(new AppError(errorMessage),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneAlreadyExistException.class)
    public ResponseEntity<AppError>  handlePhoneAlreadyExistException(
            PhoneAlreadyExistException ex){
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
