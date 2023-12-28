package com.modsen.passengerservice.controller;

import com.modsen.passengerservice.exception.PassengerNotFoundException;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.exception.ValidateException;
import com.modsen.passengerservice.service.PassengerService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.getPassengerById(id);
    }
    @GetMapping("/list-of-passengers")
    public  ResponseEntity<PassengerListResponse> getAllPassengers(){
        return passengerService.getAllPassengers();
    }
    @PostMapping("/create-passenger")
    public ResponseEntity<PassengerResponse> createPassenger(
            @RequestBody PassengerRequest passengerRequest) throws ValidateException {
        return passengerService.createPassenger(passengerRequest);
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deletePassenger(@PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.deletePassenger(id);
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<PassengerResponse> updatePassenger( @RequestBody PassengerRequest passengerRequest, @PathVariable Long id) throws PassengerNotFoundException, ValidateException {
        return passengerService.updatePassenger(id,passengerRequest);
    }

    /*@GetMapping("/get-sorted-list-of-passengers")
    public ResponseEntity<PassengerListResponse> getSortedListOfPassengers(@RequestParam String type_of_sort){
        return passengerService.getSortedListOfPassengers(type_of_sort);
    }*/

}
