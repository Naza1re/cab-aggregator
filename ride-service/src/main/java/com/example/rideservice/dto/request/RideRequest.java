package com.example.rideservice.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Data
@Setter
public class RideRequest {

    private Long id;
    @NotNull(message = "Passenger id cannot be null")
    @Min(value = 0, message = "Passenger id must be non-negative")
    private Long passengerId;
    @NotBlank(message = "Pick up address cannot be blank")
    private String pickUpAddress;
    @NotBlank(message = "Drop off  address cannot be blank")
    private String dropOffAddress;
    @NotNull(message = "Price cannot be null")
    @DecimalMax(value = "10.0", message = "Price cannot exceed 10 rubles - это дохера")
    private double price;

    private String instructions;

}
