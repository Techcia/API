package com.techcia.dtos.dashboardSale;

import com.techcia.models.Parking;
import lombok.Data;

import java.util.List;

@Data
public class ParkingDashboardSale {
    private Long id;
    private String name;
    List<SaleDashboardSale> sales;

    public void convertInEntity(Parking parking, List<SaleDashboardSale> saleDashboardSales){
        this.setId(parking.getId());
        this.setName(parking.getName());
        this.setSales(saleDashboardSales);
    }
}
