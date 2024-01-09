package com.modsen.passengerservice.exception;

public class PaginationParamException extends RuntimeException {
    public PaginationParamException(String format) {
        super(format);
    }
}
