package com.example.rideservice.dto.request;

import jakarta.validation.constraints.*;
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
    @DecimalMax(value = "10.0", message = "Price cannot exceed 10 rubles - its big count of money , pls make price less")
    private double price;
    @Size(max = 50, message = "Instructions cannot be longer than 50 characters")
    private String instructions;

}
