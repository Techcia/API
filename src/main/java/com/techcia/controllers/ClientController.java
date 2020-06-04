package com.techcia.controllers;

import com.techcia.config.ResponseError;
import com.techcia.dtos.client.ClientCreateDTO;
import com.techcia.dtos.client.ClientUpdateDTO;
import com.techcia.models.Client;
import com.techcia.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@Slf4j

@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity findMe(Principal principal){
        Optional<Client> stock = clientService.findByEmail(principal.getName());

        if(!stock.isPresent()){
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(stock.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<Client> stock = clientService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("O cliente não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(stock.get());
    }


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        Client client = clientCreateDTO.convertToEntity();
        return ResponseEntity.ok(clientService.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
        Optional<Client> stock = clientService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("O cliente não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(clientService.save(clientUpdateDTO.convertToEntity(stock.get())));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Client> stock = clientService.findById(id);
        if (!stock.isPresent()) {
            log.error("Id " + id + " is not existed");
            ResponseError response = new ResponseError("O cliente não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }




}
