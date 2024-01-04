package com.example.ratingservice.service;

import com.example.ratingservice.repository.DriverRatingRepository;
import com.example.ratingservice.repository.PassengerRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerRatingService {
    private final PassengerRatingRepository passengerRatingRepository;
}
