package com.example.rideservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Passenger {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;

}