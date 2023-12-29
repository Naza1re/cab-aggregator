package com.modsen.driverservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequest {
    private String name;
    private String surname;
    private String phone;
    private String email;
}
