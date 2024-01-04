package com.example.ratingservice.controller;

import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.service.DriverRatingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver-rating")
@RequiredArgsConstructor
public class DriverRatingController {
    private final DriverRatingService driverRatingService;

    @GetMapping("/{driver_id}")
    public ResponseEntity<DriverResponse> getRateOfDriverById(@PathVariable Long driver_id) throws DriverRatingNotFoundException {
        return driverRatingService.getDriverById(driver_id);
    }

}
