package com.modsen.passengerservice.exception.AppError;

import org.springframework.http.HttpStatusCode;

public class AppError  {

    String message;

    public AppError(String message){
        this.message = message;
    }
}
