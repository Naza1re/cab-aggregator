package com.modsen.passengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PassengerRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 16, message = "Name cannot be longer than 16 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "Phone must be a 11-digit number")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

}