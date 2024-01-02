package com.example.rideservice.dto.response;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class RideResponse {

    private Long id;

    private Long driver_id;

    private Long passenger_id;

    private String pickUpAddress;

    private String dropOfAddress;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    private boolean isActive;

}
