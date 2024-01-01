package com.example.rideservice.controller;

import com.example.rideservice.dto.RideResponse;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.model.Driver;
import com.example.rideservice.model.Passenger;
import com.example.rideservice.model.Ride;
import com.example.rideservice.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/rides")
public class RideController {
    private final RideService rideService;

    @PostMapping("/start-ride")
    public ResponseEntity<RideResponse> startRide(@RequestBody Driver driver, @RequestBody Passenger passenger){
        return rideService.startRideWithPassengerAndDriver(passenger,driver);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id) throws RideNotFoundException {
        return rideService.getRideById(id);
    }
    @PutMapping("/end-ride/{ride_Id}")
    public ResponseEntity<RideResponse> endRide(@PathVariable Long ride_Id) throws RideNotFoundException {
        return rideService.endRide(ride_Id);
    }
}
