package com.example.rideservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Driver {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private boolean available;


}
