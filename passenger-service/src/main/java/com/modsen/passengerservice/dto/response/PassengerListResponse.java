package com.modsen.passengerservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PassengerListResponse {

    private List<PassengerResponse> listOfPassengers;


}
