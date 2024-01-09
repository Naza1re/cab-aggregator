package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverPageResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.*;
import com.modsen.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriverById(
            @PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<DriverListResponse> getListOfDrivers(){
        return ResponseEntity.ok(driverService.getListOfDrivers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Long id,
            @Valid @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.ok(driverService.updateDriver(id,driverRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> deleteDriver(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(driverService.deleteDriver(id));
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(
            @Valid @RequestBody DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driverService.createDriver(driverRequest));
    }

    @GetMapping("/page")
    public ResponseEntity<DriverPageResponse> getSortedListOfDrivers(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit,
            @RequestParam(name = "type",required = false) String type
    ){
        return ResponseEntity.ok(driverService.getDriverPage(offset,limit,type));
    }

    @GetMapping("/available")
    public ResponseEntity<DriverListResponse> getAvailableDrivers(){
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }

    @PutMapping("/start-ride/{driver_id}")
    public ResponseEntity<DriverResponse> startRide(
            @PathVariable("driver_id") Long driver_id) {
        return ResponseEntity.ok(driverService.startRideWithDriverId(driver_id));
    }

    @PutMapping("/end-ride/{driver_id}")
    public ResponseEntity<DriverResponse> endRide(
            @PathVariable("driver_id") Long driver_id) {
        return ResponseEntity.ok(driverService.endRide(driver_id));
    }

    @PutMapping("/{driver_id}/start-day")
    public ResponseEntity<DriverResponse> startWorkingDayForDriverWithId(
            @PathVariable("driver_id") Long id) {
        return ResponseEntity.ok(driverService.startWorkingDay(id));
    }

    @PutMapping("/{driver_id}/end-day")
    public ResponseEntity<DriverResponse> endWorkingDayForDriverWithId(
            @PathVariable Long driver_id) {
        return ResponseEntity.ok(driverService.endWorkingDay(driver_id));
    }

}
