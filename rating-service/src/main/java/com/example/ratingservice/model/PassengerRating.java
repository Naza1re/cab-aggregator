package com.example.ratingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "passenger_rate")
public class PassengerRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Long id;
    private double rate;
}
