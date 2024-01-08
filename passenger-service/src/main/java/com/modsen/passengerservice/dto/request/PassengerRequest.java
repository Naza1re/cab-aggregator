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

    @NotBlank(message = "{name.not.blanked}")
    @Size(max = 16, message = "{name.max.value}")
    private String name;

    @NotBlank(message = "{surname.not.blanked}")
    @Size(max = 16, message = "{surname.max.value}")
    private String surname;

    @NotBlank(message = "{phone.not.blanked}")
    @Pattern(regexp = "\\d{11}", message = "{phone.invalid.message}")
    private String phone;

    @NotBlank(message = "{email.not.blanked}")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{email.invalid.message}")
    private String email;

}
