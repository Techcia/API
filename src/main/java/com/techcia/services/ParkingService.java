package com.techcia.services;

import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.repositories.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    @Transactional
    public Parking save(Parking parking){
        return parkingRepository.save(parking);
    }

    public List<Parking> findAll(){
        return parkingRepository.findAll();
    }

    public Optional<Parking> findById(Long id){
        return parkingRepository.findById(id);
    }

    public void deleteById(Long id){
        parkingRepository.deleteById(id);
    }

    public List<Parking> findByCompany(Company company){
        return parkingRepository.findByCompany(company);
    }
}
