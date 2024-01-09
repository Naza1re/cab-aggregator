package com.modsen.passengerservice.repository;

import com.modsen.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
