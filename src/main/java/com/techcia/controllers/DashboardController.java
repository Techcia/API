package com.techcia.controllers;

import com.techcia.config.ResponseError;
import com.techcia.dtos.dashboardSale.DashboardSaleRequestDTO;
import com.techcia.models.Company;
import com.techcia.services.CompanyService;
import com.techcia.services.DashboardService;
import com.techcia.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@Slf4j
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final SaleService saleService;
    private final CompanyService companyService;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping("/sales")
    public ResponseEntity dashboardSales(@Valid @RequestBody DashboardSaleRequestDTO dashboardSaleRequestDTO) throws ParseException {
        Instant instantInitial = Instant.parse( dashboardSaleRequestDTO.getInitialDate() );
        Date dateInitial = Date.from( instantInitial );
        Instant instantFinal = Instant.parse( dashboardSaleRequestDTO.getFinalDate() );
        Date dateFinal = Date.from( instantFinal );
        List<Integer> parkings = Arrays.asList(dashboardSaleRequestDTO.getParkings());
        return ResponseEntity.ok(dashboardService.dashboardSales(dateInitial, dateFinal, parkings));
    }
}
