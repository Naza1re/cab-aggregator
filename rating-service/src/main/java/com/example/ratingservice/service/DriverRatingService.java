package com.example.ratingservice.service;

import com.example.ratingservice.repository.DriverRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRatingService {
    private final DriverRatingRepository driverRatingRepository;
}
