package com.modsen.driverservice.service;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.*;
import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.repository.DriverRepository;
import com.modsen.driverservice.validation.ValidationResult;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public ResponseEntity<DriverResponse> updateDriver(Long id, DriverRequest driverRequest) throws DriverNotFoundException, ValidationException, EmailAlreadyExistException, PhoneAlreadyExistException {
        ValidationResult validationResult = validateDriverRequest(driverRequest);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        checkEmailExist(driverRequest.getEmail());
        checkPhoneExist(driverRequest.getPhone());
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            Driver driver = fromRequestToEntity(driverRequest);
            driver.setId(id);
            return new ResponseEntity<>(fromEntityToResponse(driverRepository.save(driver)),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");

    }

    public ResponseEntity<DriverResponse> createDriver(DriverRequest driverRequest) throws ValidationException, EmailAlreadyExistException, PhoneAlreadyExistException {
        ValidationResult validationResult = validateDriverRequest(driverRequest);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        checkEmailExist(driverRequest.getEmail());
        checkPhoneExist(driverRequest.getPhone());

        Driver driver = fromRequestToEntity(driverRequest);
        driver.setAvailable(false);
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

    public void checkEmailExist(String email) throws EmailAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findByEmail(email);
        if(opt_driver.isPresent()){
            throw new EmailAlreadyExistException("driver with email '"+email+"' already exist");
        }
    }
    public void checkPhoneExist(String email) throws PhoneAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findByEmail(email);
        if(opt_driver.isPresent()){
            throw new PhoneAlreadyExistException("driver with email '"+email+"' already exist");
        }
    }


    public ResponseEntity<DriverListResponse> getAvailableDrivers() {
        List<Driver> listOfAvailableDrivers = driverRepository.getAllByAvailable(true);
        List<DriverResponse> listOfAvailable =  listOfAvailableDrivers
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new ResponseEntity<>(new DriverListResponse(listOfAvailable),HttpStatus.OK);
    }


    public ResponseEntity<Page<DriverResponse>> getPaginationList(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Driver> passengerPage = driverRepository.findAll(pageable);

        Page<DriverResponse> passengerResponsePage = passengerPage.map(this::fromEntityToResponse);

        return ResponseEntity.ok(passengerResponsePage);
    }

    public ResponseEntity<DriverListResponse> getSortedListOfPassengers(String type) throws SortTypeException {
        List<Driver> sortedPassengers = switch (type.toLowerCase()) {
            case "name" -> driverRepository.findAll(Sort.by(Sort.Order.asc("name")));
            case "surname" -> driverRepository.findAll(Sort.by(Sort.Order.asc("surname")));
            default -> throw new SortTypeException("Invalid type of sort");
        };

        return new ResponseEntity<>(new DriverListResponse(sortedPassengers
                .stream()
                .map(this::fromEntityToResponse)
                .toList()),HttpStatus.OK);
    }

    public ResponseEntity<DriverResponse> startRideWithDriverId(Long driverId) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(driverId);
        if(opt_driver.isPresent()){
            opt_driver.get().setAvailable(false);
            driverRepository.save(opt_driver.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driverId+"' not found");
    }

    public ResponseEntity<DriverResponse> endRide(Long driverId) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(driverId);
        if(opt_driver.isPresent()){
            opt_driver.get().setAvailable(true);
            driverRepository.save(opt_driver.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driverId+"' not found");
    }

    public HttpStatus startWorkingDay(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            opt_driver.get().setAvailable(true);
            driverRepository.save(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }
    public HttpStatus endWorkingDay(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if(opt_driver.isPresent()){
            opt_driver.get().setAvailable(false);
            driverRepository.save(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }
}
