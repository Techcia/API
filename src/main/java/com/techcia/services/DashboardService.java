package com.techcia.services;

import com.techcia.dtos.DashboardSale;
import com.techcia.models.Company;
import com.techcia.models.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    public List<DashboardSale> dashboardSales(List<Sale> sales){
        List<DashboardSale> dashboardSales = new ArrayList<DashboardSale>();
        for(Sale sale : sales){
            DashboardSale dashboardSale = new DashboardSale();
            dashboardSale.setId(sale.getId());
            dashboardSale.setCheckin(sale.getCheckin());
            dashboardSale.setCheckout(sale.getCheckout());
            dashboardSale.setDataPay(sale.getDataPay());
            dashboardSale.setStatus(sale.getStatus());
            dashboardSale.setValue(sale.getValue());
            dashboardSales.add(dashboardSale);
        }
        return dashboardSales;
    }
}
