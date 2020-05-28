package com.techcia.controllers;


import com.techcia.config.ResponseError;
import com.techcia.dtos.CompanyUpdateDTO;
import com.techcia.dtos.ParkingCreateDTO;
import com.techcia.dtos.ParkingUpdateDTO;
import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.services.ClientService;
import com.techcia.services.CompanyService;
import com.techcia.services.ParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkings")
@Slf4j

@RequiredArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;
    private final CompanyService companyService;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ParkingCreateDTO parkingCreateDTO, Principal principal){
        Optional<Company> stock = companyService.findByEmail(principal.getName());

        if(!stock.isPresent()){
            throw new UsernameNotFoundException("Invalid token.");
        }
        Parking parking = parkingCreateDTO.convertToEntity(stock.get());
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
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(stock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Parking> stock = parkingService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("O estacionamento não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        parkingService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody ParkingUpdateDTO parkingUpdateDTO) {
        Optional<Parking> stock = parkingService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("O estacionamento não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(parkingService.save(parkingUpdateDTO.convertToEntity(stock.get())));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity findByCompany(@PathVariable Long id){
        Optional<Company> stock = companyService.findById(id);

        if(!stock.isPresent()){
            ResponseError response = new ResponseError("O estacionamento não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(parkingService.findByCompany(stock.get()));
    }

    @GetMapping("/company")
    public ResponseEntity findByCompany(Principal principal){
        Optional<Company> stock = companyService.findByEmail(principal.getName());

        if(!stock.isPresent()){
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(parkingService.findByCompany(stock.get()));
    }

}
