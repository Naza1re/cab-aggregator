package com.example.rideservice.dto.request;

import java.time.LocalDate;

public class RideRequest {

    private Long driver_id;

    private Long passenger_id;

    private String pickUpAddress;

    private String dropOfAddress;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    private boolean isActive;



}
