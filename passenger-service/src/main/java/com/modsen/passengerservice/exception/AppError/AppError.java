package com.modsen.passengerservice.exception.AppError;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
@Getter
@Setter
public class AppError  {

    private String message;

    public AppError(String message){
        this.message = message;
    }
}
