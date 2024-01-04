package com.example.ratingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "driver_rate")
public class DriverRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long id;
    private double rate;


}
