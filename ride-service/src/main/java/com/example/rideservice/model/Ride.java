package com.example.rideservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
