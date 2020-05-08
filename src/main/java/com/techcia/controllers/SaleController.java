package com.techcia.controllers;

import com.techcia.dtos.CompanyUpdateDTO;
import com.techcia.dtos.SaleCreateDTO;
import com.techcia.models.Company;
import com.techcia.models.Sale;
import com.techcia.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
@Slf4j

@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody SaleCreateDTO saleCreateDTO){
        Sale sale = saleCreateDTO.convertToEntity();
        return ResponseEntity.ok(saleService.save(sale));
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
