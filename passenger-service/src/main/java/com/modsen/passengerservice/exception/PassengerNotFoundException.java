package com.modsen.passengerservice.exception;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(String s) {
        super(s);
    }
}
