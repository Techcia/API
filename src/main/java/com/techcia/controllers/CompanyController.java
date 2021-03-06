package com.techcia.controllers;

import com.techcia.config.ResponseError;
import com.techcia.dtos.company.CompanyCreateDTO;
import com.techcia.dtos.company.CompanyUpdateDTO;
import com.techcia.models.Company;
import com.techcia.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
@Slf4j

@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CompanyCreateDTO companyCreateDTO){
        Company company = companyCreateDTO.convertToEntity();
        return ResponseEntity.ok(companyService.save(company));
    }

    @GetMapping("/me")
    public ResponseEntity findMe(Principal principal){
        Optional<Company> stock = companyService.findByEmail(principal.getName());

        if(!stock.isPresent()){
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(stock.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Company>> findAll(){
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Optional<Company> stock = companyService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("A empresa não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(stock.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Company> stock = companyService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("A empresa não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        companyService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody CompanyUpdateDTO companyUpdateDTO) {
        Optional<Company> stock = companyService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("A empresa não foi encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(companyService.save(companyUpdateDTO.convertToEntity(stock.get())));
    }


}
