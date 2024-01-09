package com.modsen.passengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PassengerPageResponse {
    private List<PassengerResponse> passengerList;
    private long totalElements;
    private int totalPages;

}