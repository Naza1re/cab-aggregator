package com.modsen.passengerservice.service;

import com.modsen.passengerservice.exception.PassengerNotFoundException;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.request.PassengerRequest;
import com.modsen.passengerservice.response.PassengerListResponse;
import com.modsen.passengerservice.response.PassengerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Passenger> opt_passenger = passengerRepository.findById(id);
        if(opt_passenger.isPresent()){
            return opt_passenger.map(this::fromEntityToResponse).get();
        }
        else
            throw new PassengerNotFoundException("passenger with id '"+id+"' not found");
    }

    public PassengerListResponse getAllPassengers(){
        List<PassengerResponse> opt_listOfPassengers = passengerRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        PassengerListResponse passengerListResponse = new PassengerListResponse();
        passengerListResponse.setListOfPassengers(opt_listOfPassengers);
        return passengerListResponse;
    }

    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        return fromEntityToResponse(passengerRepository.save(fromRequestToEntity(passengerRequest)));
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) throws PassengerNotFoundException {
            Optional<Passenger> opt_passenger = passengerRepository.findById(id);
            if(opt_passenger.isPresent()){
                Passenger passenger = fromRequestToEntity(passengerRequest);
                passenger.setId(id);
                return fromEntityToResponse(passengerRepository.save(passenger));
            }
            else
                throw new PassengerNotFoundException("passenger with id '"+id+"' not found");
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
}
