package com.modsen.passengerservice.service;

import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;

    public PassengerResponse fromEntityToResponse(Passenger passenger){
        return modelMapper.map(passenger,PassengerResponse.class);
    }
    public Passenger fromRequestToEntity(PassengerRequest passengerRequest){
        return modelMapper.map(passengerRequest, Passenger.class);
    }

    public ResponseEntity<PassengerResponse> getPassengerById(Long id) throws PassengerNotFoundException {
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if(opt_passenger.isPresent()){
            return new ResponseEntity<>(fromEntityToResponse(opt_passenger.get()),HttpStatus.OK);
        }
        else
            throw new PassengerNotFoundException("Passenger with id '"+id+"' not found");
    }

    public ResponseEntity<PassengerListResponse> getAllPassengers(){
          List<PassengerResponse> opt_listOfPassengers = passengerRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        PassengerListResponse passengerListResponse = new PassengerListResponse(opt_listOfPassengers);
        return new ResponseEntity<>(passengerListResponse,HttpStatus.OK);
    }

    public ResponseEntity<PassengerResponse> createPassenger(PassengerRequest passengerRequest) throws  EmailAlreadyExistException, PhoneAlreadyExistException {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return new ResponseEntity<>(fromEntityToResponse(savedPassenger), HttpStatus.OK);
    }

    public ResponseEntity<PassengerResponse> updatePassenger(Long id, PassengerRequest passengerRequest)
            throws PassengerNotFoundException{
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if (opt_passenger.isPresent()) {
            Passenger passenger = fromRequestToEntity(passengerRequest);
            passenger.setId(id);
            return new ResponseEntity<>(fromEntityToResponse(passengerRepository.save(passenger)), HttpStatus.OK);
        } else
            throw new PassengerNotFoundException("Passenger with id '" + id + "' not found");

    }

    
    public HttpStatus deletePassenger(Long id) throws PassengerNotFoundException {
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if (opt_passenger.isPresent()) {
            passengerRepository.delete(opt_passenger.get());
            return HttpStatus.OK;
        }
        else
            throw new PassengerNotFoundException("Passenger with id '"+id+"' no found");
    }

    public void checkEmailExist(String email) throws EmailAlreadyExistException {
        Optional<Passenger> opt_passenger = passengerRepository.findByEmail(email);
        if (opt_passenger.isPresent()) {
            throw new EmailAlreadyExistException("Passenger with email '"+email+"' already exist");
        }
    }
    public void checkPhoneExist(String phone) throws  PhoneAlreadyExistException {
        Optional<Passenger> opt_passenger = passengerRepository.findByPhone(phone);
        if (opt_passenger.isPresent()) {
            throw new PhoneAlreadyExistException("Passenger with phone '"+phone+"' already exist");
        }
    }


    public ResponseEntity<PassengerListResponse> getSortedListOfPassengers(String type) throws SortTypeException {
        List<Passenger> sortedPassengers = switch (type.toLowerCase()) {
            case "name" -> passengerRepository.findAll(Sort.by(Sort.Order.asc("name")));
            case "surname" -> passengerRepository.findAll(Sort.by(Sort.Order.asc("surname")));
            default -> throw new SortTypeException("Invalid type of sort");
        };

        return new ResponseEntity<>(new PassengerListResponse(sortedPassengers
                .stream()
                .map(this::fromEntityToResponse)
                .toList()),HttpStatus.OK);

    }

    public ResponseEntity<Page<PassengerResponse>> getPaginationList(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);

        Page<PassengerResponse> passengerResponsePage = passengerPage.map(this::fromEntityToResponse);

        return ResponseEntity.ok(passengerResponsePage);
    }


}
