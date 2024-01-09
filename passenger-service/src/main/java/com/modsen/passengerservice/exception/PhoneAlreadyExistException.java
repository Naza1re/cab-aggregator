package com.modsen.passengerservice.exception;

public class PhoneAlreadyExistException extends RuntimeException{
    public PhoneAlreadyExistException(String s){
        super(s);
    }
}
