package com.example.ratingservice.dto.responce;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PassengerListResponse {
    private List<PassengerResponse> passengerResponseList;
    public PassengerListResponse(List<PassengerResponse> passengerResponseList){
        this.passengerResponseList=passengerResponseList;
    }
}
