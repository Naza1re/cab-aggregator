package com.example.ratingservice.mapper;

import com.example.ratingservice.dto.responce.PassengerResponse;
import com.example.ratingservice.model.PassengerRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerMapper {
    private final ModelMapper modelMapper;

    public PassengerResponse fromEntityToResponse(PassengerRating passengerRating){
        return modelMapper.map(passengerRating,PassengerResponse.class);
    }





}
