package com.example.ratingservice.controller;

import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.service.PassengerRatingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger-rating")
@RequiredArgsConstructor
public class PassengerRatingController {
    private final PassengerRatingService passengerRatingService;

    @GetMapping("/{passenger_id}")
    public ResponseEntity<PassengerResponse> getPassengerRateById(@PathVariable Long passenger_id) throws PassengerRatingNotFoundException {
        return passengerRatingService.getPassengerRecordById(passenger_id);
    }
}
