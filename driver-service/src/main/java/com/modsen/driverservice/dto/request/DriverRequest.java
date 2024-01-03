package com.modsen.driverservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DriverRequest {
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
    @NotBlank(message = "Number cannot be blank")
    @Pattern(regexp = "^\\d{4} [a-zA-Z]{2}-\\d{1,}$", message = "Invalid number plate format")
    private String number;
    @NotBlank(message = "Color cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid color format")
    private String color;

    @NotBlank(message = "Model cannot be blank")
    private String model;
}
