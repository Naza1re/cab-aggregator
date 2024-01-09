package com.example.rideservice.controller;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.exception.RideAlreadyAcceptedException;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.service.RideService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/rides")
public class RideController {
    private final RideService rideService;
    @PatchMapping("/{ride_id}/accept-ride-driver/{driver_id}")
    public ResponseEntity<RideResponse> acceptRideByDriver(
            @PathVariable Long driver_id,
            @PathVariable Long ride_id) throws RideNotFoundException, RideAlreadyAcceptedException {
        return rideService.acceptRide(ride_id,driver_id);
    }

    @PatchMapping("/{ride_id}/cancel-ride-driver/{driver_id}")
    public HttpStatus cancelRideByDriver(
            @PathVariable Long driver_id,
            @PathVariable Long ride_id) throws RideNotFoundException {
        return rideService.cancelRide(ride_id,driver_id);
    }

    @PostMapping("/find-ride")
    public ResponseEntity<RideResponse> findRide(@Valid @RequestBody RideRequest rideRequest){
        return rideService.findRide(rideRequest);
    }

    @PutMapping("/{ride_id}/start-ride")
    public ResponseEntity<RideResponse> startRide(
            @PathVariable Long ride_id) throws RideNotFoundException, RideAlreadyAcceptedException {
        return rideService.startRideWithPassengerAndDriver(ride_id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id) throws RideNotFoundException {
        return rideService.getRideById(id);
    }

    @PutMapping("/{ride_id}/end-ride")
    public ResponseEntity<RideResponse> endRide(@PathVariable Long ride_id) throws RideNotFoundException, RideAlreadyAcceptedException {
        return rideService.endRide(ride_id);
    }

    @GetMapping("/get-passenger-list-of-rides/{passenger_id}")
    public ResponseEntity<RideListResponse> getListOfRidesByPassengerId(@PathVariable Long passenger_id){
        return rideService.getListOfRidesByPassengerId(passenger_id);
    }

    @GetMapping("/get-driver-list-of-rides/{driver_id}")
    public ResponseEntity<RideListResponse> getListOfRidesByDriverId(@PathVariable Long driver_id){
        return rideService.getListOfRidesByDriverId(driver_id);
    }
}
