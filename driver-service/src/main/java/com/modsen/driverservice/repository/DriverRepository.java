package com.modsen.driverservice.repository;

import com.modsen.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    Optional<Driver> findByEmail(String email);
    List<Driver> getAllByAvailable(boolean available);
    Optional<Driver> findByNumber(String number);
    Optional<Driver> findByPhone(String phone);
}
