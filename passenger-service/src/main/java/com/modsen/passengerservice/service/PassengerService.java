package com.modsen.passengerservice.service;

import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.util.Messages;
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

    public PassengerResponse getPassengerById(Long id) throws PassengerNotFoundException {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(()->
                new PassengerNotFoundException(String.format(Messages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
            return fromEntityToResponse(passenger);

    }

    public PassengerListResponse getAllPassengers(){
          List<PassengerResponse> listOfPassengers = passengerRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new PassengerListResponse(listOfPassengers);
    }

    public PassengerResponse createPassenger(PassengerRequest passengerRequest) throws  EmailAlreadyExistException, PhoneAlreadyExistException {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return fromEntityToResponse(savedPassenger);
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest)
            throws PassengerNotFoundException, EmailAlreadyExistException, PhoneAlreadyExistException {

        preUpdateEmailCheck(id,passengerRequest);
        preUpdatePhoneCheck(id,passengerRequest);

        Passenger passenger = passengerRepository.findById(id).orElseThrow(()->
                new PassengerNotFoundException(String.format(Messages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
            passenger = fromRequestToEntity(passengerRequest);
            passenger.setId(id);
            return fromEntityToResponse(passengerRepository.save(passenger));

    }

    public PassengerResponse deletePassenger(Long id) throws PassengerNotFoundException {
       Passenger passenger = passengerRepository.findById(id).orElseThrow(()->
               new PassengerNotFoundException(String.format(Messages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
            passengerRepository.delete(passenger);
            return fromEntityToResponse(passenger);

    }

    public void preUpdateEmailCheck(Long passenger_id,PassengerRequest passengerRequest) throws EmailAlreadyExistException, PassengerNotFoundException {
        Optional<Passenger> listOfPassengers = passengerRepository.findById(passenger_id);
        if(listOfPassengers.isPresent()){
            if(!listOfPassengers.get().getEmail().equals(passengerRequest.getEmail())){
                checkEmailExist(passengerRequest.getEmail());
            }
        }
        else {
            throw new PassengerNotFoundException(String.format(Messages.PASSENGER_NOT_FOUND_EXCEPTION, passenger_id));
        }
    }
    public void preUpdatePhoneCheck(Long passenger_id,PassengerRequest passengerRequest) throws PassengerNotFoundException, PhoneAlreadyExistException {
        Optional<Passenger> opt_passenger = passengerRepository.findById(passenger_id);
        if(opt_passenger.isPresent()){
            if(!opt_passenger.get().getPhone().equals(passengerRequest.getPhone())){
                checkPhoneExist(passengerRequest.getPhone());
            }
        }
        else {
            throw new PassengerNotFoundException(String.format(Messages.PASSENGER_NOT_FOUND_EXCEPTION,passenger_id));
        }
    }

    public void checkEmailExist(String email) throws EmailAlreadyExistException {

        Optional<Passenger> opt_passenger = passengerRepository.findByEmail(email);
        if (opt_passenger.isPresent()) {
            throw new EmailAlreadyExistException(String.format(Messages.PASSENGER_WITH_EMAIL_ALREADY_EXIST,email));
        }
    }
    public void checkPhoneExist(String phone) throws  PhoneAlreadyExistException {
        Optional<Passenger> opt_passenger = passengerRepository.findByPhone(phone);
        if (opt_passenger.isPresent()) {
            throw new PhoneAlreadyExistException(String.format(Messages.PASSENGER_WITH_PHONE_ALREADY_EXIST,phone));
        }
    }

    public PassengerListResponse getSortedListOfPassengers(String type) throws SortTypeException {
        List<Passenger> sortedPassengers = switch (type.toLowerCase()) {
            case "name" -> passengerRepository.findAll(Sort.by(Sort.Order.asc("name")));
            case "surname" -> passengerRepository.findAll(Sort.by(Sort.Order.asc("surname")));
            default -> throw new SortTypeException(String.format(Messages.INVALID_TYPE_OF_SORT));
        };

        return new PassengerListResponse(sortedPassengers
                .stream()
                .map(this::fromEntityToResponse)
                .toList());

    }

    public Page<PassengerResponse> getPaginationList(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);
        return passengerPage.map(this::fromEntityToResponse);
    }


}
