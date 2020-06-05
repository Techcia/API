package com.techcia.dtos.dashboardSale;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardSaleResposeDTO {
    public Long totalSales;
    public Double totalSalesInReal;
    public Double mediaSalesByHour;
    public Double mediaSalesByParking;
    public List<SaleDashboardSale> sales;

    public void dashboardNull(){
        this.setTotalSales((long) 0);
        this.setTotalSalesInReal(0.0);
        this.setMediaSalesByParking(0.0);
        this.setMediaSalesByHour(0.0);
        this.setSales(new ArrayList<>());
    }
}