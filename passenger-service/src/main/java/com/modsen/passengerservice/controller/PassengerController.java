package com.modsen.passengerservice.controller;

import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public PassengerResponse getPassengerById(
            @PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.getPassengerById(id);
    }

    @GetMapping("/list")
    public PassengerListResponse getAllPassengers(){
        return passengerService.getAllPassengers();
    }

    @PostMapping()
    public PassengerResponse createPassenger(
           @Valid @RequestBody PassengerRequest passengerRequest) throws EmailAlreadyExistException, PhoneAlreadyExistException {
        return passengerService.createPassenger(passengerRequest);
    }

    @DeleteMapping("/{id}")
    public PassengerResponse deletePassenger(
            @PathVariable Long id) throws PassengerNotFoundException {
        return passengerService.deletePassenger(id);
    }

    @PutMapping("/{id}")
    public PassengerResponse updatePassenger(
            @Valid @RequestBody PassengerRequest passengerRequest,
            @PathVariable Long id) throws PassengerNotFoundException, PhoneAlreadyExistException, EmailAlreadyExistException {
        return passengerService.updatePassenger(id,passengerRequest);
    }

    @GetMapping("/page")
    public Page<PassengerResponse> getSortedListOfPassengers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return passengerService.getPaginationList(offset,limit);
    }

    @GetMapping("/sorted-list")
    public PassengerListResponse sortedListOfPassengers(
            @RequestParam String type) throws SortTypeException {
        return passengerService.getSortedListOfPassengers(type);
    }

}
