package com.modsen.driverservice.exception;

public class CarNumberAlreadyExistException extends RuntimeException {
    public CarNumberAlreadyExistException(String s) {
        super(s);
    }
}
