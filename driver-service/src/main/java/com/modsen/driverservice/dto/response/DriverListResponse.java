package com.modsen.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
public class DriverListResponse {
    private List<DriverResponse> driverResponseList;

}
