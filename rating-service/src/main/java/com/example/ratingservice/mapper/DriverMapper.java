package com.example.ratingservice.mapper;

import com.example.ratingservice.dto.request.DriverRequest;
import com.example.ratingservice.dto.responce.DriverResponse;
import com.example.ratingservice.model.DriverRating;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DriverMapper {

    private final ModelMapper modelMapper;

    public DriverResponse fromEntityToResponse(DriverRating driverRating){
        return modelMapper.map(driverRating,DriverResponse.class);
    }
    public DriverRating fromRequestToEntity(DriverRequest driverRequest){
        return modelMapper.map(driverRequest,DriverRating.class);
    }
}
