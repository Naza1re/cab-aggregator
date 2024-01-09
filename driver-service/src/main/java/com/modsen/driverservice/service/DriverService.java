package com.modsen.driverservice.service;

import com.modsen.driverservice.dto.request.DriverRequest;
import com.modsen.driverservice.dto.response.DriverListResponse;
import com.modsen.driverservice.dto.response.DriverPageResponse;
import com.modsen.driverservice.dto.response.DriverResponse;
import com.modsen.driverservice.exception.*;
import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.repository.DriverRepository;
import com.modsen.driverservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    public DriverResponse fromEntityToResponse(Driver driver){
        return modelMapper.map(driver, DriverResponse.class);
    }
    public Driver fromRequestToEntity(DriverRequest driverRequest){
        return modelMapper.map(driverRequest,Driver.class);
    }

    public DriverResponse getDriverById(Long id) {
       Driver driver = getOrThrow(id);
       return fromEntityToResponse(driver);
    }

    public DriverListResponse getListOfDrivers() {
        List<DriverResponse> driverResponseList = driverRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new DriverListResponse(driverResponseList);
    }

    public DriverResponse updateDriver(Long id, DriverRequest driverRequest) {

        preUpdateEmailCheck(id,driverRequest);
        preUpdatePhoneCheck(id,driverRequest);
        preUpdateCarNumberCheck(id,driverRequest);

        driverRepository.findById(id)
                .orElseThrow(()-> new DriverNotFoundException(String.format(ExceptionMessages.DRIVER_NOT_FOUND_EXCEPTION,id)));
            Driver driver = fromRequestToEntity(driverRequest);
            driver.setId(id);
            return fromEntityToResponse(driverRepository.save(driver));
    }

    public DriverResponse createDriver(DriverRequest driverRequest) {

        checkEmailExist(driverRequest.getEmail());
        checkPhoneExist(driverRequest.getPhone());
        checkCarNumberExist(driverRequest.getNumber());

        Driver driver = fromRequestToEntity(driverRequest);
        driver.setAvailable(false);
        Driver savedDriver = driverRepository.save(driver);

        return fromEntityToResponse(savedDriver);
    }

    public DriverResponse deleteDriver(Long id) {
       Driver driver = getOrThrow(id);
       driverRepository.delete(driver);
       return fromEntityToResponse(driver);
    }

    public void preUpdateEmailCheck(Long driver_id,DriverRequest driverRequest) {
        Driver driver = getOrThrow(driver_id);
            if(!driver.getEmail().equals(driverRequest.getEmail())){
                checkEmailExist(driverRequest.getEmail());
            }
    }

    public void preUpdatePhoneCheck(Long driver_id,DriverRequest driverRequest) {
        Driver driver = getOrThrow(driver_id);
            if(!driver.getPhone().equals(driverRequest.getPhone())){
                checkPhoneExist(driverRequest.getPhone());
            }
    }

    public void preUpdateCarNumberCheck(Long driver_id,DriverRequest driverRequest) {
        Driver driver = getOrThrow(driver_id);
            if(!driver.getNumber().equals(driverRequest.getNumber())){
                checkCarNumberExist(driverRequest.getNumber());
            }
    }

    public void checkEmailExist(String email) {
        if (driverRepository.existsByEmail(email) ){
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_EMAIL_ALREADY_EXIST,email));
        }
    }

    public void checkPhoneExist(String phone) {
        if (driverRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_PHONE_ALREADY_EXIST,phone));
        }
    }

    public void checkCarNumberExist(String carNum) {
        if (driverRepository.existsByNumber(carNum)) {
            throw new CarNumberAlreadyExistException(String.format(ExceptionMessages.DRIVER_WITH_CAR_NUMBER_ALREADY_EXIST, carNum));
        }
    }

    public DriverListResponse getAvailableDrivers() {
        List<Driver> listOfAvailableDrivers = driverRepository.getAllByAvailable(true);
        List<DriverResponse> listOfAvailable =  listOfAvailableDrivers
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new DriverListResponse(listOfAvailable);
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

    public DriverPageResponse getDriverPage(int page, int size, String orderBy) {

        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Driver> driverPage = driverRepository.findAll(pageRequest);

        List<Driver> retrievedDrivers = driverPage.getContent();
        long total = driverPage.getTotalElements();

        List<DriverResponse> passengers = retrievedDrivers.stream()
                .map(this::fromEntityToResponse)
                .toList();

        return DriverPageResponse.builder()
                .driverList(passengers)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    private void validateSortingParameter(String orderBy) {
        List<String> fieldNames = Arrays.stream(DriverResponse.class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        if (!fieldNames.contains(orderBy)) {
            throw new SortTypeException(ExceptionMessages.INVALID_TYPE_OF_SORT);
        }
    }

    public DriverResponse startRideWithDriverId(Long driverId) {
        Driver driver = getOrThrow(driverId);
            driver.setAvailable(false);
            driverRepository.save(driver);
            return fromEntityToResponse(driver);
    }

    public DriverResponse endRide(Long driverId) throws DriverNotFoundException {
        Driver driver = getOrThrow(driverId);
            driver.setAvailable(true);
            driverRepository.save(driver);
            return fromEntityToResponse(driver);
    }

    public DriverResponse startWorkingDay(Long id) throws DriverNotFoundException {
        Driver driver = getOrThrow(id);
            driver.setAvailable(true);
            driverRepository.save(driver);
            return fromEntityToResponse(driver);
    }

    public DriverResponse endWorkingDay(Long id) throws DriverNotFoundException {
        Driver driver = getOrThrow(id);
            driver.setAvailable(false);
            driverRepository.save(driver);
            return fromEntityToResponse(driver);
    }

    public Driver getOrThrow(Long id){
        return driverRepository.findById(id)
                .orElseThrow(()-> new DriverNotFoundException(String.format(ExceptionMessages.DRIVER_NOT_FOUND_EXCEPTION,id)));
    }
}
