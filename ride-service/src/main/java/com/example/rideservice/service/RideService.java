package com.example.rideservice.service;

import com.example.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository repository;
}
