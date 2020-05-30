package com.techcia.controllers;

import com.techcia.config.ResponseError;
import com.techcia.models.Company;
import com.techcia.services.CompanyService;
import com.techcia.services.DashboardService;
import com.techcia.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final SaleService saleService;
    private final CompanyService companyService;

    @PreAuthorize("hasRole('COMPANY')")
    @GetMapping("/sales")
    public ResponseEntity dashboardSales(@RequestParam(name = "initialDate") String initialD, @RequestParam(name = "finalDate") String finalD, Principal principal) throws ParseException {
        if(initialD == null || finalD == null || initialD == ""  || finalD == "" ){
            ResponseError response = new ResponseError("Os parâmetros data incial e data final são obrigatórios");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<Company> stock = companyService.findByEmail(principal.getName());
        if(!stock.isPresent()){
            ResponseError response = new ResponseError("Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Instant instantInitial = Instant.parse( initialD );
        Date dateInitial = Date.from( instantInitial ) ;
        Instant instantFinal = Instant.parse( finalD );
        Date dateFinal = Date.from( instantFinal ) ;
//        List<Sale> sales = saleService.findByCompanyByDate(stock.get(), dateInitial, dateFinal);
        return ResponseEntity.ok(dashboardService.dashboardSales(stock.get(), dateInitial, dateFinal));
    }
}
