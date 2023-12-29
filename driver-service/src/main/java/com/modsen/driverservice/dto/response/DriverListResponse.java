package com.modsen.driverservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class DriverListResponse {
    private List<DriverResponse> driverResponseList;

    public DriverListResponse(List<DriverResponse> driverResponseList){
        this.driverResponseList=driverResponseList;
    }
}
