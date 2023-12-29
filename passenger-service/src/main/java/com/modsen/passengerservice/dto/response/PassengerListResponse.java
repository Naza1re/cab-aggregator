package com.modsen.passengerservice.dto.response;

import com.modsen.passengerservice.model.Passenger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PassengerListResponse {

    private List<PassengerResponse> listOfPassengers;


    public PassengerListResponse(List<PassengerResponse> sortedPassengers) {
        this.listOfPassengers=sortedPassengers;
    }
}
