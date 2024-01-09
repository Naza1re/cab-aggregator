package com.modsen.passengerservice.service;

import com.modsen.passengerservice.dto.response.PassengerPageResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.util.ExceptionMessages;
import com.modsen.passengerservice.util.SortTypeConstants;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;

    public PassengerResponse fromEntityToResponse(Passenger passenger){
        return modelMapper.map(passenger, PassengerResponse.class);
    }

    public Passenger fromRequestToEntity(PassengerRequest passengerRequest){
        return modelMapper.map(passengerRequest, Passenger.class);
    }

    public PassengerResponse getPassengerById(Long id) throws PassengerNotFoundException {
        Passenger passenger = getOrThrow(id);
        return fromEntityToResponse(passenger);

    }

    public PassengerListResponse getAllPassengers(){
          List<PassengerResponse> listOfPassengers = passengerRepository.findAll().stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new PassengerListResponse(listOfPassengers);
    }

    public PassengerResponse createPassenger(PassengerRequest passengerRequest) throws  EmailAlreadyExistException, PhoneAlreadyExistException {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return fromEntityToResponse(savedPassenger);
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest)
            throws PassengerNotFoundException, EmailAlreadyExistException, PhoneAlreadyExistException {

        preUpdateEmailCheck(id,passengerRequest);
        preUpdatePhoneCheck(id,passengerRequest);

        passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
            Passenger passenger = fromRequestToEntity(passengerRequest);
            passenger.setId(id);
            return fromEntityToResponse(passengerRepository.save(passenger));

    }

    public PassengerResponse deletePassenger(Long id) throws PassengerNotFoundException {
       Passenger passenger = getOrThrow(id);
            passengerRepository.delete(passenger);
            return fromEntityToResponse(passenger);

    }

    public void preUpdateEmailCheck(Long passengerId, PassengerRequest passengerRequest)
            throws EmailAlreadyExistException, PassengerNotFoundException {

        Passenger passenger = getOrThrow(passengerId);

        if (!passenger.getEmail().equals(passengerRequest.getEmail())) {
            checkEmailExist(passengerRequest.getEmail());
        }
    }

    public void preUpdatePhoneCheck(Long passengerId, PassengerRequest passengerRequest)
            throws PassengerNotFoundException, PhoneAlreadyExistException {
        Passenger passenger = getOrThrow(passengerId);

        if (!passenger.getPhone().equals(passengerRequest.getPhone())) {
            checkPhoneExist(passengerRequest.getPhone());
        }
    }

    public void checkEmailExist(String email) throws EmailAlreadyExistException {

        if (passengerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_EMAIL_ALREADY_EXIST,email));
        }
    }

    public void checkPhoneExist(String phone) throws  PhoneAlreadyExistException {

        if (passengerRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_PHONE_ALREADY_EXIST,phone));
        }
    }

    public PassengerListResponse getSortedListOfPassengers(String type) throws SortTypeException {
        List<Passenger> sortedPassengers = switch (type.toLowerCase()) {
            case "name" -> passengerRepository.findAll(Sort.by(Sort.Order.asc(SortTypeConstants.SORT_TYPE_NAME)));
            case "surname" -> passengerRepository.findAll(Sort.by(Sort.Order.asc(SortTypeConstants.SORT_TYPE_SURNAME)));
            default -> throw new SortTypeException(String.format(ExceptionMessages.INVALID_TYPE_OF_SORT));
        };

        return new PassengerListResponse(sortedPassengers.stream()
                .map(this::fromEntityToResponse)
                .toList());

    }

    public PassengerPageResponse getPaginationList(
            @Min(value = 0, message = "Offset must be greater than or equal to 0") Integer offset,
            @Min(value = 1, message = "Limit must be greater than or equal to 1") Integer limit) throws PaginationParamException {
        if (offset < 0 || limit < 1){
            throw new PaginationParamException(String.format(ExceptionMessages.PAGINATION_FORMAT_EXCEPTION));
        }
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);

        List<PassengerResponse> passengerResponses = passengerPage.map(this::fromEntityToResponse).getContent();
        return new PassengerPageResponse(passengerResponses, passengerPage.getTotalElements(), passengerPage.getTotalPages());
    }

    public Passenger getOrThrow(Long id) throws PassengerNotFoundException {
        return passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
    }


}
