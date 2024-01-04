package com.example.ratingservice.dto.responce;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerResponse {
    private Long id;
    private Long passenger;
    private double rate;
}
