package com.example.ratingservice.dto.responce;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class DriverListResponse {
    private List<DriverResponse> driverResponseList;
    public DriverListResponse(List<DriverResponse> driverResponseList){
        this.driverResponseList=driverResponseList;
    }
}
