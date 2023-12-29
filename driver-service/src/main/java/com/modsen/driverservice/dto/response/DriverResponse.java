package com.modsen.driverservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DriverResponse {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
}
