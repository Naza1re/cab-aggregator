package com.example.ratingservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    @NotNull(message = "rate cannot be null")
    @DecimalMin(value = "1.0", inclusive = true, message = "rate must be a number from 1 to 5")
    @DecimalMax(value = "5.0", inclusive = true, message = "rate must be a number from 1 to 5")
    private double rate;
}
