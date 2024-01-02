package com.example.rideservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RideListResponse {
    private List<RideResponse> rideResponseList;
    public RideListResponse(List<RideResponse> rideListResponses){
        this.rideResponseList=rideListResponses;
    }
}
