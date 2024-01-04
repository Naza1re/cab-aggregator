package com.example.ratingservice.service;

import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import com.example.ratingservice.repository.PassengerRatingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerRatingService {
    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerMapper passengerMapper;

    public ResponseEntity<PassengerResponse> getPassengerRecordById(Long passengerId) throws PassengerRatingNotFoundException {
        Optional<PassengerRating> opt_passengerRating = passengerRatingRepository.findById(passengerId);
        if(opt_passengerRating.isPresent()){
            return new ResponseEntity<>(passengerMapper.fromEntityToResponse(opt_passengerRating.get()), HttpStatus.OK);
        }
        else
            throw new PassengerRatingNotFoundException("Passenger record with passenger id  '"+passengerId+"' not found");
    }
}
