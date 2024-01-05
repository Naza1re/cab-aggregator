package com.example.ratingservice.controller;

import com.example.ratingservice.dto.request.Request;
import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.exception.PassengelAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.service.PassengerRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger-rating")
@RequiredArgsConstructor
public class PassengerRatingController {
    private final PassengerRatingService passengerRatingService;

    @GetMapping("/{passenger_id}")
    public ResponseEntity<PassengerResponse> getPassengerRateById(@PathVariable Long passenger_id) throws PassengerRatingNotFoundException {
        return passengerRatingService.getPassengerRecordById(passenger_id);
    }
    @PostMapping("/{passenger_id}/creat-passenger-rate")
    public HttpStatus creatingPassenger(@PathVariable Long passenger_id) throws PassengelAlreadyExistException {
        return passengerRatingService.createPassenger(passenger_id);
    }
    @PutMapping("/{passenger_id}/update-passenger-rate")
    public ResponseEntity<PassengerResponse> updatePassengerRate(
            @Valid @RequestBody Request passengerRequest,
            @PathVariable Long passenger_id) throws PassengerRatingNotFoundException {
        return passengerRatingService.updatePassengerRating(passenger_id,passengerRequest.getRate());
    }
    @DeleteMapping("/{passenger_id}")
    public HttpStatus deletePassengerRecord(@PathVariable Long passenger_id) throws PassengerRatingNotFoundException {
        return passengerRatingService.deletePassengerRecord(passenger_id);
    }
}
