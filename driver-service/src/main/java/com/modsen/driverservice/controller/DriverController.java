package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.exception.ValidationException;
import com.modsen.driverservice.service.DriverService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
            @RequestBody DriverRequest driverRequest) throws DriverNotFoundException, ValidationException {
        return driverService.updateDriver(id,driverRequest);
    }
    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteDriver(
            @PathVariable Long id) throws DriverNotFoundException {
        return driverService.deleteDriver(id);
    }
    @PostMapping("/create-driver")
    public ResponseEntity<DriverResponse> createDriver(
            @RequestBody DriverRequest driverRequest) throws ValidationException {
        return driverService.createDriver(driverRequest);
    }
}
