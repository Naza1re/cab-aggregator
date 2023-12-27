package com.modsen.passengerservice.controller;

import com.modsen.passengerservice.exception.PassengerNotFoundException;
import com.modsen.passengerservice.request.PassengerRequest;
import com.modsen.passengerservice.response.PassengerListResponse;
import com.modsen.passengerservice.response.PassengerResponse;
import com.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public PassengerResponse getPassengerById(@PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.getPassengerById(id);
    }
    @GetMapping("/list-of-passengers")
    public PassengerListResponse getAllPassengers(){
        return passengerService.getAllPassengers();
    }
    @PostMapping("/create-passenger")
    public PassengerResponse createPassenger(@RequestBody PassengerRequest passengerRequest){
        return passengerService.createPassenger(passengerRequest);
    }
    @DeleteMapping("/{id}/delete")
    public HttpStatus deletePassenger(@PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.deletePassenger(id);
    }
    @PutMapping("/{id}/update")
    public PassengerResponse updatePassenger(@RequestBody PassengerRequest passengerRequest, @PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.updatePassenger(id,passengerRequest);
    }

}
