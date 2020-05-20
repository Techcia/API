package com.techcia.controllers;

import com.techcia.constants.SaleConstants;
import com.techcia.dtos.SaleCheckinDTO;
import com.techcia.models.Client;
import com.techcia.models.Parking;
import com.techcia.models.Sale;
import com.techcia.services.ClientService;
import com.techcia.services.ParkingService;
import com.techcia.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
@Slf4j

@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    private final ClientService clientService;
    private final ParkingService parkingService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/checkin")
    public ResponseEntity checkin(@Valid @RequestBody SaleCheckinDTO saleCheckinDTO, Principal principal){
        Optional<Client> stockClient = clientService.findByEmail(principal.getName());
        if(!stockClient.isPresent()){
            throw new UsernameNotFoundException("Invalid token");
        }

        Optional<Parking> stockParking = parkingService.findById(saleCheckinDTO.getParkingId());
        if(!stockParking.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id Parking " + saleCheckinDTO.getParkingId() + " is not existed");
        }

        Sale sale = saleCheckinDTO.convertToEntity(stockClient.get(), stockParking.get());
        return ResponseEntity.ok(saleService.save(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/generate_pay/{id}")
    public ResponseEntity generatePay(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }
        Sale sale = stockSale.get();
        if(!sale.getStatus().equals(SaleConstants.ABERTO)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O status do ticket está fechado");
        }
        return ResponseEntity.ok(saleService.generatePay(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/pay/{id}")
    public ResponseEntity pay(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }
        Sale sale = stockSale.get();
        if(!sale.getStatus().equals(SaleConstants.ABERTO)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O status do ticket está fechado");
        }
        return ResponseEntity.ok(saleService.pay(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/checkout/{id}")
    public ResponseEntity checkout(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }
        Sale sale = stockSale.get();
        if(!sale.getStatus().equals(SaleConstants.PAGO)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The sale is not paid");
        }
        return ResponseEntity.ok(saleService.checkout(sale));
    }

    @GetMapping
    public ResponseEntity<List<Sale>> findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Optional<Sale> stock = saleService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }

        return ResponseEntity.ok(stock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Sale> stock = saleService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id " + id + " is not existed");
        }

        saleService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
