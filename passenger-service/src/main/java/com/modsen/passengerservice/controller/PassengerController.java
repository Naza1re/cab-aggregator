package com.modsen.passengerservice.controller;

import com.modsen.passengerservice.dto.response.PassengerPageResponse;
import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(
            @PathVariable Long id) throws PassengerNotFoundException {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<PassengerListResponse> getAllPassengers(){
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(
           @Valid @RequestBody PassengerRequest passengerRequest) throws EmailAlreadyExistException, PhoneAlreadyExistException {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.createPassenger(passengerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponse> deletePassenger(
            @PathVariable Long id) throws PassengerNotFoundException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(passengerService.deletePassenger(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @Valid @RequestBody PassengerRequest passengerRequest,
            @PathVariable Long id) throws PassengerNotFoundException, PhoneAlreadyExistException, EmailAlreadyExistException {
        return ResponseEntity.ok(passengerService.updatePassenger(id,passengerRequest));
    }

    @GetMapping("/page")
    public PassengerPageResponse getSortedListOfPassengers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ) throws PaginationParamException {
        return ResponseEntity.ok(passengerService.getPaginationList(offset,limit)).getBody();
    }

    @GetMapping("/sorted-list")
    public ResponseEntity<PassengerListResponse> sortedListOfPassengers(
            @RequestParam String type) throws SortTypeException {
        return ResponseEntity.ok(passengerService.getSortedListOfPassengers(type));
    }

}
