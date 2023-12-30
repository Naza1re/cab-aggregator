package com.modsen.driverservice.service;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.exception.ValidationException;
import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.repository.DriverRepository;
import com.modsen.driverservice.validation.ValidationResult;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public DriverResponse fromEntityToResponse(Driver driver){
        return modelMapper.map(driver, DriverResponse.class);
    }
    public Driver fromRequestToEntity(DriverRequest driverRequest){
        return modelMapper.map(driverRequest,Driver.class);
    }


    public ResponseEntity<DriverResponse> getDriverById(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()), HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("driver with id '"+id+"' not found");
    }

    public ResponseEntity<DriverListResponse> getListOfDrivers() {
        List<DriverResponse> driverResponseList = driverRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new ResponseEntity<>(new DriverListResponse(driverResponseList),HttpStatus.OK);
    }

    public ResponseEntity<DriverResponse> updateDriver(Long id, DriverRequest driverRequest) throws DriverNotFoundException, ValidationException {
        ValidationResult validationResult = validateDriverRequest(driverRequest);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            Driver driver = fromRequestToEntity(driverRequest);
            driver.setId(id);
            return new ResponseEntity<>(fromEntityToResponse(driverRepository.save(driver)),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");

    }

    public ResponseEntity<DriverResponse> createDriver(DriverRequest driverRequest) throws ValidationException {
        ValidationResult validationResult = validateDriverRequest(driverRequest);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        Driver driver = fromRequestToEntity(driverRequest);
        Driver savedDriver = driverRepository.save(driver);
        return new ResponseEntity<>(fromEntityToResponse(savedDriver),HttpStatus.OK);
    }

    public HttpStatus deleteDriver(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            driverRepository.delete(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }
    private ValidationResult validateDriverRequest(DriverRequest driverRequest) {
        Set<ConstraintViolation<DriverRequest>> violations = validator.validate(driverRequest);
        Map<String, String> errorMap = violations.stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        return new ValidationResult(errorMap);
    }








}
