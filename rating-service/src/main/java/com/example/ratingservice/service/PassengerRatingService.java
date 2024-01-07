package com.example.ratingservice.service;

import com.example.ratingservice.dto.responce.DriverListResponse;
import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.dto.responce.PassengerListResponse;
import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.exception.IncorrectIdException;
import com.example.ratingservice.exception.PassengelAlreadyExistException;
import com.example.ratingservice.exception.PassengerRatingNotFoundException;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.PassengerRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerRatingService {
    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerMapper passengerMapper;

    public ResponseEntity<PassengerResponse> getPassengerRecordById(Long passengerId) throws PassengerRatingNotFoundException {
        Optional<PassengerRating> opt_passengerRating = passengerRatingRepository.findPassengerRatingByPassenger(                                                                                                       passengerId);
        if (opt_passengerRating.isPresent()) {
            return new ResponseEntity<>(passengerMapper.fromEntityToResponse(opt_passengerRating.get()), HttpStatus.OK);
        } else
            throw new PassengerRatingNotFoundException("Passenger record with passenger id  '" + passengerId + "' not found");
    }

    public ResponseEntity<PassengerResponse> createPassenger(Long passengerId) throws PassengelAlreadyExistException, IncorrectIdException {
        checkPassengerById(passengerId);
        checkCorrectPassengerId(passengerId);
        PassengerRating passengerRating = new PassengerRating();
        passengerRating.setPassenger(passengerId);
        passengerRating.setRate(5.0);
        passengerRatingRepository.save(passengerRating);
        return new ResponseEntity<>(passengerMapper.fromEntityToResponse(passengerRating),HttpStatus.CREATED);
    }

    public void checkPassengerById(Long passenger_id) throws PassengelAlreadyExistException {
        Optional<PassengerRating> opt_passenger = passengerRatingRepository.findPassengerRatingByPassenger(passenger_id);
        if (opt_passenger.isPresent()) {
            throw new PassengelAlreadyExistException("Passenger record with passenger id '" + passenger_id + "' already exist");
        }
    }

    public void checkCorrectPassengerId(Long passenger_id) throws IncorrectIdException {
        if(passenger_id<=0){
            throw new IncorrectIdException("Incorrect driver id '"+passenger_id+"'");
        }
    }

    public ResponseEntity<PassengerResponse> updatePassengerRating(Long passenger_id, double rate) throws PassengerRatingNotFoundException {
        Optional<PassengerRating> opt_passenger = passengerRatingRepository.findPassengerRatingByPassenger(passenger_id);
        if (opt_passenger.isPresent()) {
            double newRate = (opt_passenger.get().getRate() + rate) / 2;
            opt_passenger.get().setRate(newRate);
            passengerRatingRepository.save(opt_passenger.get());
            return new ResponseEntity<>(passengerMapper.fromEntityToResponse(opt_passenger.get()), HttpStatus.OK);
        } else
            throw new PassengerRatingNotFoundException("Passenger record with passenger id  '" + passenger_id + "' not found");
    }

    public HttpStatus deletePassengerRecord(Long passengerId) throws PassengerRatingNotFoundException {
        Optional<PassengerRating> opt_passenger = passengerRatingRepository.findPassengerRatingByPassenger(passengerId);
        if (opt_passenger.isPresent()) {
            passengerRatingRepository.delete(opt_passenger.get());
            return HttpStatus.OK;
        }
        else
            throw new PassengerRatingNotFoundException("Passenger record with passenger id  '" + passengerId+ "' not found");
    }

    public ResponseEntity<PassengerListResponse> getAllPassengersRecords() {

        List<PassengerResponse> passengerRatings = passengerRatingRepository.findAll()
                .stream()
                .map(passengerMapper::fromEntityToResponse)
                .toList();
        PassengerListResponse passengerListResponse = new PassengerListResponse(passengerRatings);

        return new ResponseEntity<>(passengerListResponse,HttpStatus.OK);
    }
}

