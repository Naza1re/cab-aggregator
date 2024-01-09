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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


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

    public DriverResponse getDriverById(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if (opt_driver.isPresent()) {
            return fromEntityToResponse(opt_driver.get());
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }

    public ResponseEntity<DriverListResponse> getListOfDrivers() {
        List<DriverResponse> driverResponseList = driverRepository.findAll()
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new ResponseEntity<>(new DriverListResponse(driverResponseList),HttpStatus.OK);
    }


    public ResponseEntity<DriverResponse> updateDriver(Long id, DriverRequest driverRequest) throws DriverNotFoundException, EmailAlreadyExistException, PhoneAlreadyExistException, CarNumberAlreadyExistException {

        preUpdateEmailCheck(id,driverRequest);
        preUpdatePhoneCheck(id,driverRequest);
        preUpdateCarNumberCheck(id,driverRequest);

        Optional<Driver> opt_driver = driverRepository.findById(id);
        if (opt_driver.isPresent()) {
            Driver driver = fromRequestToEntity(driverRequest);
            driver.setId(id);
            return new ResponseEntity<>(fromEntityToResponse(driverRepository.save(driver)),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");

    }

    public ResponseEntity<DriverResponse> createDriver(DriverRequest driverRequest) throws EmailAlreadyExistException, PhoneAlreadyExistException, CarNumberAlreadyExistException {

        checkEmailExist(driverRequest.getEmail());
        checkPhoneExist(driverRequest.getPhone());
        checkCarNumberExist(driverRequest.getNumber());

        Driver driver = fromRequestToEntity(driverRequest);
        driver.setAvailable(false);
        Driver savedDriver = driverRepository.save(driver);
        return new ResponseEntity<>(fromEntityToResponse(savedDriver),HttpStatus.OK);
    }

    public HttpStatus deleteDriver(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if (opt_driver.isPresent()) {
            driverRepository.delete(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }
    public void preUpdateEmailCheck(Long driver_id,DriverRequest driverRequest) throws EmailAlreadyExistException, DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(driver_id);
        if (opt_driver.isPresent()) {
            if(!opt_driver.get().getEmail().equals(driverRequest.getEmail())){
                checkEmailExist(driverRequest.getEmail());
            }
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driver_id+"' not found");
    }
    public void preUpdatePhoneCheck(Long driver_id,DriverRequest driverRequest) throws DriverNotFoundException, PhoneAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findById(driver_id);
        if (opt_driver.isPresent()) {
            if(!opt_driver.get().getPhone().equals(driverRequest.getPhone())){
                checkPhoneExist(driverRequest.getPhone());
            }
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driver_id+"' not found");
    }
    public void preUpdateCarNumberCheck(Long driver_id,DriverRequest driverRequest) throws CarNumberAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findById(driver_id);
        if (opt_driver.isPresent()) {
            if(!opt_driver.get().getNumber().equals(driverRequest.getNumber())){
                System.out.println(driverRequest.getNumber());
                checkCarNumberExist(driverRequest.getNumber());
            }
        }
    }

    public void checkEmailExist(String email) throws EmailAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findByEmail(email);
        if (opt_driver.isPresent()) {
            throw new EmailAlreadyExistException("Driver with email '"+email+"' already exist");
        }
    }
    public void checkPhoneExist(String phone) throws PhoneAlreadyExistException {
        Optional<Driver> opt_driver = driverRepository.findByPhone(phone);
        if (opt_driver.isPresent()) {
            throw new PhoneAlreadyExistException("Driver with phone '"+phone+"' already exist");
        }
    }
    public void checkCarNumberExist(String carNum) throws CarNumberAlreadyExistException {
        System.out.println(carNum);
        Optional<Driver> opt_driver = driverRepository.findByNumber(carNum);
        if (opt_driver.isPresent()) {
            throw new CarNumberAlreadyExistException("Car with number '"+carNum+"' already exist");
        }
    }

    public ResponseEntity<DriverListResponse> getAvailableDrivers() {
        List<Driver> listOfAvailableDrivers = driverRepository.getAllByAvailable(true);
        List<DriverResponse> listOfAvailable =  listOfAvailableDrivers
                .stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new ResponseEntity<>(new DriverListResponse(listOfAvailable),HttpStatus.OK);
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

    public ResponseEntity<Page<DriverResponse>> getPaginationList(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Driver> passengerPage = driverRepository.findAll(pageable);

        Page<DriverResponse> passengerResponsePage = passengerPage.map(this::fromEntityToResponse);

        return ResponseEntity.ok(passengerResponsePage);
    }


    public ResponseEntity<DriverListResponse> getSortedListOfPassengers(String type) throws SortTypeException {
        List<Driver> sortedPassengers = switch (type.toLowerCase()) {
            case "name" -> driverRepository.findAll(Sort.by(Sort.Order.asc("name")));
            case "surname" -> driverRepository.findAll(Sort.by(Sort.Order.asc("surname")));
            default -> throw new SortTypeException("Invalid type of sort");
        };

        return new ResponseEntity<>(new DriverListResponse(sortedPassengers
                .stream()
                .map(this::fromEntityToResponse)
                .toList()),HttpStatus.OK);
    }

    public ResponseEntity<DriverResponse> startRideWithDriverId(Long driverId) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(driverId);
        if (opt_driver.isPresent()) {
            opt_driver.get().setAvailable(false);
            driverRepository.save(opt_driver.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driverId+"' not found");
    }

    public ResponseEntity<DriverResponse> endRide(Long driverId) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(driverId);
        if (opt_driver.isPresent()) {
            opt_driver.get().setAvailable(true);
            driverRepository.save(opt_driver.get());
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with id '"+driverId+"' not found");
    }

    public HttpStatus startWorkingDay(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if (opt_driver.isPresent()) {
            opt_driver.get().setAvailable(true);
            driverRepository.save(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }

    public HttpStatus endWorkingDay(Long id) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findById(id);
        if (opt_driver.isPresent()) {
            opt_driver.get().setAvailable(false);
            driverRepository.save(opt_driver.get());
            return HttpStatus.OK;
        }
        else
            throw new DriverNotFoundException("Driver with id '"+id+"' not found");
    }

    public ResponseEntity<DriverResponse> getDriverByCarNumber(String carNumber) throws DriverNotFoundException {
        Optional<Driver> opt_driver = driverRepository.findByNumber(carNumber);
        if (opt_driver.isPresent()) {
            return new ResponseEntity<>(fromEntityToResponse(opt_driver.get()),HttpStatus.OK);
        }
        else
            throw new DriverNotFoundException("Driver with car number '"+carNumber+"' not found");
    }
}
