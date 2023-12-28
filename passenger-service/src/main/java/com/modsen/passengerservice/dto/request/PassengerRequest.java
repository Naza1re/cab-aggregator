package com.modsen.passengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 16, message = "Name cannot be longer than 16 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @Pattern(regexp = "\\d{10}", message = "Phone must be a 10-digit number")
    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

}
