package com.example.ratingservice.mapper;

import com.example.ratingservice.dto.request.PassengerRequest;
import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.model.PassengerRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerMapper {
    private final ModelMapper modelMapper;

    public PassengerResponse fromEntityToResponse(PassengerRating passengerRating){
        return modelMapper.map(passengerRating,PassengerResponse.class);
    }
    public PassengerRating fromRequestToEntity(PassengerRequest passengerRequest){
        return modelMapper.map(passengerRequest,PassengerRating.class);
    }




}
