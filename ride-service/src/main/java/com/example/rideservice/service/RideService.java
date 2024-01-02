package com.example.rideservice.service;

import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.model.Driver;
import com.example.rideservice.model.Passenger;
import com.example.rideservice.model.Ride;
import com.example.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;



    public Ride fromResponseToEntity(RideResponse response){
        return modelMapper.map(response,Ride.class);
    }
    public RideResponse fromEntityToResponse(Ride ride){
        return modelMapper.map(ride,RideResponse.class);
    }

    public ResponseEntity<RideResponse> startRideWithPassengerAndDriver(Passenger passenger, Driver driver) {
        Ride ride = new Ride();
        ride.setDriver_id(driver.getId());
        ride.setPassenger_id(passenger.getId());
        ride.setStartDate(LocalDate.from(LocalDateTime.now()));
        rideRepository.save(ride);
        return new ResponseEntity<>(fromEntityToResponse(ride), HttpStatus.OK);
    }

    public ResponseEntity<RideResponse> getRideById(Long id) throws RideNotFoundException {
        Optional<Ride> opt_ride = rideRepository.findById(id);
        if(opt_ride.isPresent()){
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+id+"' not found");
    }

   public ResponseEntity<RideResponse> endRide(Long rideId) throws RideNotFoundException {
        Optional<Ride> opt_ride = rideRepository.findById(rideId);
        if(opt_ride.isPresent()){
            opt_ride.get().setEndDate(LocalDate.from(LocalDateTime.now()));
            rideRepository.save(opt_ride.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+rideId+"' not found");

    }

    public ResponseEntity<RideListResponse> getListOfRidesByPassengerId(Long passengerId) {
        List<RideResponse> rideList = rideRepository.getAllByPassenger_id(passengerId)
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        RideListResponse response = new RideListResponse(rideList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    public ResponseEntity<RideListResponse> getListOfRidesByDriverId(Long driverId) {
        List<RideResponse> rideList = rideRepository.getAllByDriver_id(driverId)
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        RideListResponse response = new RideListResponse(rideList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
