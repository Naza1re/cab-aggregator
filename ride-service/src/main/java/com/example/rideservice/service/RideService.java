package com.example.rideservice.service;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.exception.RideAlreadyAcceptedException;
import com.example.rideservice.exception.RideNotFoundException;
import com.example.rideservice.exception.RideNotHaveDriverException;
import com.example.rideservice.model.Ride;
import com.example.rideservice.repository.RideRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RideService {
    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;

    public Ride fromRequestToEntity(RideRequest request){
        return modelMapper.map(request,Ride.class);
    }

    public RideResponse fromEntityToResponse(Ride ride){
        return modelMapper.map(ride,RideResponse.class);
    }

    public ResponseEntity<RideResponse> startRideWithPassengerAndDriver(Long ride_id) throws RideNotFoundException, RideAlreadyAcceptedException {
        checkRideHasDriver(ride_id);
        Optional<Ride> opt_ride = rideRepository.findById(ride_id);
        if (opt_ride.isPresent()) {
            opt_ride.get().setStartDate(LocalDate.now());
            opt_ride.get().setActive(true);
            rideRepository.save(opt_ride.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+ride_id+"' not found");

    }

    public ResponseEntity<RideResponse> getRideById(Long id) throws RideNotFoundException {
        Optional<Ride> opt_ride = rideRepository.findById(id);
        if (opt_ride.isPresent()) {
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+id+"' not found");
    }

    public void checkRideHasDriver(Long id){
        Optional<Ride> opt_ride = rideRepository.findById(id);
        if(opt_ride.isPresent()){
            if(opt_ride.get().getDriverId()==null){
                throw new RideNotHaveDriverException("Ride with id '"+id+"' not have driver yet");
            }
        }
    }

   public ResponseEntity<RideResponse> endRide(Long rideId) throws RideNotFoundException, RideAlreadyAcceptedException {
        checkRideHasDriver(rideId);
        Optional<Ride> opt_ride = rideRepository.findById(rideId);
        if (opt_ride.isPresent()) {
            opt_ride.get().setEndDate(LocalDate.now());
            opt_ride.get().setActive(false);
            rideRepository.save(opt_ride.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+rideId+"' not found");

    }

    public ResponseEntity<RideListResponse> getListOfRidesByPassengerId(Long passengerId) {
        List<RideResponse> rideList = rideRepository.getAllByPassengerId(passengerId)
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        RideListResponse response = new RideListResponse(rideList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<RideListResponse> getListOfRidesByDriverId(Long driverId) {
        List<RideResponse> rideList = rideRepository.getAllByDriverId(driverId)
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        RideListResponse response = new RideListResponse(rideList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<RideResponse> findRide(RideRequest rideRequest) {
        Ride ride = fromRequestToEntity(rideRequest);
        rideRepository.save(ride);
        return new ResponseEntity<>(fromEntityToResponse(ride),HttpStatus.OK);
    }

    public ResponseEntity<RideResponse> acceptRide(Long rideId, Long driverId) throws RideNotFoundException, RideAlreadyAcceptedException {
        checkRideAlreadyExist(rideId);
        Optional<Ride> opt_ride = rideRepository.findById(rideId);
        if (opt_ride.isPresent()) {
            opt_ride.get().setDriverId(driverId);
            rideRepository.save(opt_ride.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_ride.get()),HttpStatus.OK);
        }
        else
            throw new RideNotFoundException("Ride with id '"+rideId+"' not found");
    }

    public HttpStatus cancelRide(Long rideId, Long driverId) throws RideNotFoundException {
        Optional<Ride> opt_ride = rideRepository.findById(rideId);
        if (opt_ride.isPresent()) {
            return HttpStatus.OK;
        }
        else
            throw new RideNotFoundException("Ride with id '"+rideId+"' not founda");
    }

    public void checkRideAlreadyExist(Long ride_id) throws RideAlreadyAcceptedException {
        if (rideRepository.existsByIdAndDriverIdIsNotNull(ride_id)) {
            throw new RideAlreadyAcceptedException("Ride with id '"+ride_id+"' already has driver");
        }
    }
}
