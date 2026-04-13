package com.suyash.carrental.repository;

import com.suyash.carrental.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByAvailableTrue();
    List<Vehicle> findByCategoryAndAvailableTrue(String category);
    boolean existsByRegistrationNumber(String registrationNumber);
}
