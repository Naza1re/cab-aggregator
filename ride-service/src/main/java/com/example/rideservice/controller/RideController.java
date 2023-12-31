package com.example.rideservice.controller;

import com.example.rideservice.service.RideService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RideController {

    private final RideService rideService;
}
