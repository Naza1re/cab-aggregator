package com.modsen.passengerservice.response;

import java.util.List;

public class PassengerListResponse {
    List<PassengerResponse> listOfPassengers;

    public PassengerListResponse(List<PassengerResponse> listOfPassengers) {
        this.listOfPassengers=listOfPassengers;
    }
}
