package com.modsen.passengerservice.service;

import com.modsen.passengerservice.dto.response.PassengerPageResponse;
import com.modsen.passengerservice.dto.response.PassengerResponse;
import com.modsen.passengerservice.exception.*;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.dto.request.PassengerRequest;
import com.modsen.passengerservice.dto.response.PassengerListResponse;
import com.modsen.passengerservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.lang.reflect.Field;
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

    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = getOrThrow(id);
        return fromEntityToResponse(passenger);

    }

    public PassengerListResponse getAllPassengers(){
          List<PassengerResponse> listOfPassengers = passengerRepository.findAll().stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new PassengerListResponse(listOfPassengers);
    }

    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return fromEntityToResponse(savedPassenger);
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {

        preUpdateEmailCheck(id,passengerRequest);
        preUpdatePhoneCheck(id,passengerRequest);

        passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
            Passenger passenger = fromRequestToEntity(passengerRequest);
            passenger.setId(id);
            return fromEntityToResponse(passengerRepository.save(passenger));

    }

    public PassengerResponse deletePassenger(Long id) {
       Passenger passenger = getOrThrow(id);
            passengerRepository.delete(passenger);
            return fromEntityToResponse(passenger);

    }

    public void preUpdateEmailCheck(Long passengerId, PassengerRequest passengerRequest) {

        Passenger passenger = getOrThrow(passengerId);

        if (!passenger.getEmail().equals(passengerRequest.getEmail())) {
            checkEmailExist(passengerRequest.getEmail());
        }
    }

    public void preUpdatePhoneCheck(Long passengerId, PassengerRequest passengerRequest) {
        Passenger passenger = getOrThrow(passengerId);

        if (!passenger.getPhone().equals(passengerRequest.getPhone())) {
            checkPhoneExist(passengerRequest.getPhone());
        }
    }

    public void checkEmailExist(String email) {

        if (passengerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_EMAIL_ALREADY_EXIST,email));
        }
    }

    public void checkPhoneExist(String phone) {

        if (passengerRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_PHONE_ALREADY_EXIST,phone));
        }
    }

    public PageRequest getPageRequest(int page, int size, String orderBy) {
        if (page < 1 || size < 1) {
            throw new PaginationParamException(String.format(ExceptionMessages.PAGINATION_FORMAT_EXCEPTION));
        }

        PageRequest pageRequest;
        if (orderBy == null) {
            pageRequest = PageRequest.of(page - 1, size);
        } else {
            validateSortingParameter(orderBy);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orderBy));
        }

        return pageRequest;
    }
    private void validateSortingParameter(String orderBy) {
        List<String> fieldNames = Arrays.stream(PassengerResponse.class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        if (!fieldNames.contains(orderBy)) {
            throw new SortTypeException(ExceptionMessages.INVALID_TYPE_OF_SORT);
        }
    }
    public PassengerPageResponse getPassengerPage(int page, int size, String orderBy) {

        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Passenger> passengersPage = passengerRepository.findAll(pageRequest);

        List<Passenger> retrievedPassengers = passengersPage.getContent();
        long total = passengersPage.getTotalElements();

        List<PassengerResponse> passengers = retrievedPassengers.stream()
                .map(this::fromEntityToResponse)
                .toList();

        return PassengerPageResponse.builder()
                .passengerList(passengers)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    public Passenger getOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
    }

}
