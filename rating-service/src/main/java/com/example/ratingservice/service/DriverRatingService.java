package com.example.ratingservice.service;

import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.exception.DriverAlreadyExistException;
import com.example.ratingservice.exception.DriverRatingNotFoundException;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.repository.DriverRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverRatingService {
    private final DriverRatingRepository driverRatingRepository;
    private final DriverMapper driverMapper;

    public ResponseEntity<DriverResponse> getDriverById(Long driverId) throws DriverRatingNotFoundException {
        Optional<DriverRating> opt_driverRating = driverRatingRepository.findById(driverId);
        if(opt_driverRating.isPresent()){
            return new ResponseEntity<>(driverMapper.fromEntityToResponse(opt_driverRating.get()), HttpStatus.OK);
        }
        else
            throw new DriverRatingNotFoundException("Driver record with driver id '"+driverId+"' not found");
    }

    public HttpStatus createDriver(Long driverId) throws DriverAlreadyExistException {
        checkDriverExist(driverId);
        DriverRating driverRating = new DriverRating();
        driverRating.setDriver_id(driverId);
        driverRating.setRate(5.0);
        driverRatingRepository.save(driverRating);
        return HttpStatus.CREATED;
    }
    public ResponseEntity<DriverResponse> updateDriverRate(Long driver_id,double rate) throws DriverRatingNotFoundException {
        Optional<DriverRating> opt_driver = driverRatingRepository.findByDriver_id(driver_id);
        if(opt_driver.isPresent()){
            double newRate = (opt_driver.get().getRate()+rate)/2;
            opt_driver.get().setRate(newRate);
            driverRatingRepository.save(opt_driver.get());
            return new ResponseEntity<>(driverMapper.fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverRatingNotFoundException("Driver record with driver id '"+driver_id+"' not found");
    }

    public HttpStatus deleteDriverRecord(Long driver_id) throws DriverRatingNotFoundException {
        Optional<DriverRating> opt_driver = driverRatingRepository.findByDriver_id(driver_id);
        if(opt_driver.isPresent()){
            driverRatingRepository.delete(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverRatingNotFoundException("Driver record with driver id '"+driver_id+"' not found");
    }

    public void checkDriverExist(Long driver_id) throws DriverAlreadyExistException {
        Optional<DriverRating> opt_driver = driverRatingRepository.findByDriver_id(driver_id);
        if(!opt_driver.isPresent()){
            throw new DriverAlreadyExistException("Driver record with driver_id '"+driver_id+"' already exist");
        }
    }
}
