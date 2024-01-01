package com.example.rideservice.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ride")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long driver_id;

    private Long passenger_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private double price;

    private boolean isActive;

    private String instructions;

}
