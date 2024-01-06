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

    private Long driverId;

    private Long passengerId;

    private String pickUpAddress;

    private String dropOffAddress;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    private boolean isActive;

}
