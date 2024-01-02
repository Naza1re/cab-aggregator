package com.example.rideservice.repository;

import com.example.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    List<Ride> getAllByPassenger_id(Long passenger_id);
    List<Ride> getAllByDriver_id(Long driver_id);
}
