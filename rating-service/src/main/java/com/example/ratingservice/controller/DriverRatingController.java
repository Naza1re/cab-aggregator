package com.example.ratingservice.controller;

import com.example.ratingservice.dto.request.Request;
import com.example.ratingservice.dto.responce.DriverListResponse;
import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.exception.DriverAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.exception.IncorrectIdException;
import com.example.ratingservice.service.DriverRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver-rating")
@RequiredArgsConstructor
public class DriverRatingController {
    private final DriverRatingService driverRatingService;
    @GetMapping("/list-of-drivers")
    public ResponseEntity<DriverListResponse> getListOfDrivers(){
        return driverRatingService.getAllDriversRecords();
    }

    @GetMapping("/{driver_id}")
    public ResponseEntity<DriverResponse> getRateOfDriverById(
            @PathVariable Long driver_id) throws DriverRatingNotFoundException {
        return driverRatingService.getDriverById(driver_id);
    }

    @PostMapping("/{driver_id}/create-driver-rate")
    public ResponseEntity<DriverResponse> createDriverRecord(@PathVariable Long driver_id) throws DriverAlreadyExistException, IncorrectIdException {
        return driverRatingService.createDriver(driver_id);
    }

    @PutMapping("/{driver_id}/update-driver-rate")
    public ResponseEntity<DriverResponse> updateDriverRating(
            @PathVariable Long driver_id,
            @Valid @RequestBody Request driverRequest) throws DriverRatingNotFoundException {
        return driverRatingService.updateDriverRate(driver_id,driverRequest.getRate());
    }

    @DeleteMapping("/{driver_id}")
    public HttpStatus deleteDriver(@PathVariable Long driver_id) throws DriverRatingNotFoundException {
        return driverRatingService.deleteDriverRecord(driver_id);
    }


}
