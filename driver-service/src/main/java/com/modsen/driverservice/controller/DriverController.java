package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.*;
import com.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;


    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriverById(
            @PathVariable Long id) throws DriverNotFoundException {
        return driverService.getDriverById(id);
    }
    @GetMapping("/list-of-drivers")
    public ResponseEntity<DriverListResponse> getListOfDrivers(){
        return driverService.getListOfDrivers();
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Long id,
            @RequestBody DriverRequest driverRequest) throws DriverNotFoundException, ValidationException, PhoneAlreadyExistException, EmailAlreadyExistException {
        return driverService.updateDriver(id,driverRequest);
    }
    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteDriver(
            @PathVariable Long id) throws DriverNotFoundException {
        return driverService.deleteDriver(id);
    }
    @PostMapping("/create-driver")
    public ResponseEntity<DriverResponse> createDriver(
            @RequestBody DriverRequest driverRequest) throws ValidationException, EmailAlreadyExistException, PhoneAlreadyExistException {
        return driverService.createDriver(driverRequest);
    }

    @GetMapping("/get-list-with-pagination")
    public ResponseEntity<Page<DriverResponse>> getSortedListOfDrivers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return driverService.getPaginationList(offset,limit);
    }

    @GetMapping("/sorted-list")
    public ResponseEntity<DriverListResponse> SortedListOfDrivers(@RequestParam String type) throws  SortTypeException {
        return driverService.getSortedListOfPassengers(type);
    }
    @GetMapping("/get-available-drivers")
    public ResponseEntity<DriverListResponse> getAvailableDrivers(){
        return driverService.getAvailableDrivers();
    }
    @PutMapping("/start-ride/{driver_id}")
    public ResponseEntity<DriverResponse> startRide(@PathVariable("driver_id") Long driver_id) throws DriverNotFoundException {
        return driverService.startRideWithDriverId(driver_id);
    }
    @PutMapping("/end-ride/{driver_id}")
    public ResponseEntity<DriverResponse> endRide(@PathVariable("driver_id") Long driver_id) throws DriverNotFoundException {
        return driverService.endRide(driver_id);
    }
    @PutMapping("/{driver_id}/start-working-day")
    public HttpStatus startWorkingDayForDriverWithId(@PathVariable("driver_id") Long id) throws DriverNotFoundException {
        return driverService.startWorkingDay(id);
    }
    @PutMapping("/{driver_id}/end-working-day")
    public HttpStatus endWorkingDayForDriverWithId(@PathVariable Long driver_id) throws DriverNotFoundException {
        return driverService.endWorkingDay(driver_id);
    }
    @GetMapping("/get-driver-by-car-number/{car_number}")
    public ResponseEntity<DriverResponse> getDriverByCarNumber(@PathVariable String car_number) throws DriverNotFoundException {
        return driverService.getDriverByCarNumber(car_number);
    }

}
