package com.modsen.passengerservice.repository;

import com.modsen.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    Optional<Passenger> findByPhone(String phone);
    Passenger findByName(String name);
    Passenger findBySurname(String surname);
    Optional<Passenger> findByEmail(String email);
}
