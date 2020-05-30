package com.techcia.services;

import com.techcia.dtos.dashboardSale.ParkingDashboardSale;
import com.techcia.dtos.dashboardSale.SaleDashboardSale;
import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.models.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ParkingService parkingService;
    private final SaleService saleService;
    private SaleDashboardSale dashboardSale;

    public List<ParkingDashboardSale> dashboardSales(Company company, Date initialDate, Date finalDate) {
        List<Parking> parkings = parkingService.findByCompany(company);
        List<ParkingDashboardSale> listParkingDashboardSale = new ArrayList<ParkingDashboardSale>();
        for (Parking parking : parkings) {
            List<Sale> sales = this.saleService.findByParkingByDate(parking, initialDate, finalDate);
            List<SaleDashboardSale> listSaleDashboardSale = new ArrayList<SaleDashboardSale>();
            ParkingDashboardSale parkingDashboardSale = new ParkingDashboardSale();
            for (com.techcia.models.Sale sale : sales) {
                SaleDashboardSale saleDashboardSale = new SaleDashboardSale();
                saleDashboardSale.convertSaleInEntity(sale);
                listSaleDashboardSale.add(saleDashboardSale);
            }
            parkingDashboardSale.convertInEntity(parking, listSaleDashboardSale);
            listParkingDashboardSale.add(parkingDashboardSale);
        }
        return listParkingDashboardSale;
    }

}
