package com.example.rideservice.dto.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RideRequest {
    private Long passengerId;
    private String pickUpAddress;
    private String dropOffAddress;
    private double price;




}
