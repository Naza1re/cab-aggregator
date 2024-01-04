package com.example.ratingservice.service;

import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverRatingService {
    private final DriverRatingRepository driverRatingRepository;
    private final DriverMapper driverMapper;

    public ResponseEntity<DriverResponse> getDriverById(Long driverId) throws DriverRatingNotFoundException {
        Optional<DriverRating> opt_driverRating = driverRatingRepository.findById(driverId);
        if(opt_driverRating.isPresent()){
            return new ResponseEntity<>(driverMapper.fromEntityToResponse(opt_driverRating.get()), HttpStatus.OK);
        }
        else
            throw new DriverRatingNotFoundException("Driver record with driver id '"+driverId+"' not found");
    }
}
