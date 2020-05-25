package com.techcia.controllers;

import com.mercadopago.*;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import com.techcia.config.ResponseError;
import com.techcia.constants.PaymentConstants;
import com.techcia.constants.SaleConstants;
import com.techcia.dtos.PaymentDTO;
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
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Parking> stockParking = parkingService.findById(saleCheckinDTO.getParkingId());
        if(!stockParking.isPresent()){
            ResponseError response = new ResponseError("O estacionamento não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Sale sale = saleCheckinDTO.convertToEntity(stockClient.get(), stockParking.get());
        return ResponseEntity.ok(saleService.save(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/generate_pay/{id}")
    public ResponseEntity generatePay(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Sale sale = stockSale.get();
        if(sale.getStatus().equals(SaleConstants.FECHADO)){
            ResponseError response = new ResponseError("O ticket já realizou checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(sale.getStatus().equals(SaleConstants.PAGO)){
            ResponseError response = new ResponseError("O ticket já se encontra pago");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(saleService.generatePay(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/pay/{id}")
    public ResponseEntity pay(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Sale sale = stockSale.get();
        if(sale.getStatus().equals(SaleConstants.FECHADO)){
            ResponseError response = new ResponseError("O ticket já realizou checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(sale.getStatus().equals(SaleConstants.PAGO)){
            ResponseError response = new ResponseError("O ticket já se encontra pago");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(saleService.pay(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/pay/{id}")
    public ResponseEntity payment(@Valid @RequestBody PaymentDTO paymentDTO, @PathVariable Long id) throws MPException, MPConfException {
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Sale sale = stockSale.get();
        if(sale.getStatus().equals(SaleConstants.FECHADO)){
            ResponseError response = new ResponseError("O ticket já realizou checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(sale.getStatus().equals(SaleConstants.PAGO)){
            ResponseError response = new ResponseError("O ticket já se encontra pago");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        MercadoPago.SDK.setAccessToken("TEST-3857643372912665-052500-dd48c9f0950e6ce030a47e62de7cd4b6-463408128");

        Payment payment = paymentDTO.convertToEntity();

        payment.save();
        System.out.println(payment.getStatus());
        if(payment.getStatus() == null){
            ResponseError response = new ResponseError("O cartão foi recusado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(!payment.getStatus().toString().equals("approved")){
            ResponseError response = new ResponseError("O cartão foi recusado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(saleService.pay(sale));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/checkout/{id}")
    public ResponseEntity checkout(@PathVariable Long id, Principal principal){
        Optional<Sale> stockSale = saleService.findById(id);
        if(!stockSale.isPresent()){
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Sale sale = stockSale.get();
        if(sale.getStatus().equals(SaleConstants.ABERTO)){
            ResponseError response = new ResponseError("O ticket não está pago");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(sale.getStatus().equals(SaleConstants.FECHADO)){
            ResponseError response = new ResponseError("O ticket já realizou checkout");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(stock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Sale> stock = saleService.findById(id);
        if (!stock.isPresent()) {
            ResponseError response = new ResponseError("O ticket não foi encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        saleService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
