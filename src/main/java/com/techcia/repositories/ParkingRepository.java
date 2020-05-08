package com.techcia.repositories;

import com.techcia.models.Company;
import com.techcia.models.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findById(Long id);

    List<Parking> findByCompany(Company company);
}
