package com.techcia.controllers;


import com.techcia.dtos.CompanyUpdateDTO;
import com.techcia.dtos.ParkingCreateDTO;
import com.techcia.dtos.ParkingUpdateDTO;
import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.services.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking")
@Slf4j

@RequiredArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ParkingCreateDTO parkingCreateDTO){
        Parking parking = parkingCreateDTO.convertToEntity();
        return ResponseEntity.ok(parkingService.save(parking));
    }

    @GetMapping
    public ResponseEntity<List<Parking>> findAll(){
        return ResponseEntity.ok(parkingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Optional<Parking> stock = parkingService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }

        return ResponseEntity.ok(stock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Parking> stock = parkingService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }

        parkingService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody ParkingUpdateDTO parkingUpdateDTO) {
        Optional<Parking> stock = parkingService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }
        return ResponseEntity.ok(parkingService.save(parkingUpdateDTO.convertToEntity(stock.get())));
    }

}
