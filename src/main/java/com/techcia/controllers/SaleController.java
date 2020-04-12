package com.techcia.controllers;

import com.techcia.dtos.SaleCreateDTO;
import com.techcia.models.Sale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
@Slf4j

@RequiredArgsConstructor
public class SaleController {
    private final saleService saleService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody SaleCreateDTO saleCreateDTO){
        Sale sale = saleCreateDTO.convertToEntity();
        return ResponseEntity.ok(saleService.save(sale));
    }
}
