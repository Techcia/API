package com.techcia.dtos.dashboardSale;

import lombok.Data;

import java.util.List;

@Data
public class DashboardSaleResposeDTO {
    public Long totalSales;
    public Double totalSalesInReal;
    public Double mediaSalesByHour;
    public Double mediaSalesByParking;
    public List<ParkingDashboardSale> parkings;
}