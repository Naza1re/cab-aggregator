package com.modsen.passengerservice.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerResponse {
    private Long id;

    private String name;

    private String surname;

    private String phone;

    private String email;
}
