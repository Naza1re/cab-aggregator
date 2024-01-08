package com.modsen.passengerservice.dto.response;

import com.modsen.passengerservice.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PassengerListResponse {

    private List<PassengerResponse> listOfPassengers;

}
