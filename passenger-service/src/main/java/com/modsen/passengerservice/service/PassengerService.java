package com.modsen.passengerservice.service;

import com.modsen.passengerservice.exception.PassengerNotFoundException;
import com.modsen.passengerservice.exception.ValidateException;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.validation.ValidationResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;


    public PassengerResponse fromEntityToResponse(Passenger passenger){
        return modelMapper.map(passenger,PassengerResponse.class);
    }
    public Passenger fromRequestToEntity(PassengerRequest passengerRequest){
        return modelMapper.map(passengerRequest, Passenger.class);
    }

    public ResponseEntity<PassengerResponse> getPassengerById(Long id) throws PassengerNotFoundException {
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if(opt_passenger.isPresent()){
            return new ResponseEntity<>(opt_passenger.map(this::fromEntityToResponse).get(),HttpStatus.OK);
        }
        else
            throw new PassengerNotFoundException("passenger with id '"+id+"' not found");
    }

    public ResponseEntity<PassengerListResponse> getAllPassengers(){
          List<PassengerResponse> opt_listOfPassengers = passengerRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        PassengerListResponse passengerListResponse = new PassengerListResponse();
        passengerListResponse.setListOfPassengers(opt_listOfPassengers);
        return new ResponseEntity<>(passengerListResponse,HttpStatus.OK);
    }

    public ResponseEntity<PassengerResponse> createPassenger(PassengerRequest passengerRequest) throws ValidateException {
        ValidationResult validationResult = validatePassengerRequest(passengerRequest);
        if (!validationResult.isValid()) {
            throw new ValidateException(validationResult.getErrors());
        }

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return new ResponseEntity<>(fromEntityToResponse(savedPassenger), HttpStatus.OK);
    }
    public ResponseEntity<PassengerResponse> updatePassenger(Long id, PassengerRequest passengerRequest)
            throws PassengerNotFoundException, ValidateException {
        ValidationResult validationResult = validatePassengerRequest(passengerRequest);
        if (!validationResult.isValid()) {
            throw new ValidateException(validationResult.getErrors());
        }

        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if (opt_passenger.isPresent()) {
            Passenger passenger = fromRequestToEntity(passengerRequest);
            passenger.setId(id);
            return new ResponseEntity<>(fromEntityToResponse(passengerRepository.save(passenger)), HttpStatus.OK);
        } else {
            throw new PassengerNotFoundException("passenger with id '" + id + "' not found");
        }
    }



    public HttpStatus deletePassenger(Long id) throws PassengerNotFoundException {
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if(opt_passenger.isPresent()){
            passengerRepository.delete(opt_passenger.get());
            return HttpStatus.OK;
        }
        else
            throw new PassengerNotFoundException("passenger with id '"+id+"' no found");
    }

    private ValidationResult validatePassengerRequest(PassengerRequest passengerRequest) {
        Set<ConstraintViolation<PassengerRequest>> violations = validator.validate(passengerRequest);
        Map<String, String> errorMap = violations.stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        return new ValidationResult(errorMap);
    }

}
