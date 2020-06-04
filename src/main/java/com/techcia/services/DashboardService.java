package com.techcia.services;

import com.techcia.dtos.dashboardSale.DashboardSaleResposeDTO;
import com.techcia.dtos.dashboardSale.ParkingDashboardSale;
import com.techcia.dtos.dashboardSale.SaleDashboardSale;
import com.techcia.models.Company;
import com.techcia.models.Parking;
import com.techcia.models.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ParkingService parkingService;
    private final SaleService saleService;

    public DashboardSaleResposeDTO dashboardSales(Company company, Date initialDate, Date finalDate){
        DashboardSaleResposeDTO dashboardSaleResposeDTO = new DashboardSaleResposeDTO();

        // Find total and sum value sales and parking
        Map<String, Object> countAndSumValueSales = saleService.countAndSumValueSales(company, initialDate, finalDate);
        dashboardSaleResposeDTO.setTotalSales(Long.parseLong(countAndSumValueSales.get("totalSales").toString()));
        dashboardSaleResposeDTO.setTotalSalesInReal(Double.parseDouble(countAndSumValueSales.get("totalSalesInReal").toString()));

        List<Parking> parkings = parkingService.findByCompany(company);

        //calculate media by hour and by parking
        Double mediaByHour = calculateMediaByHour(initialDate, finalDate, dashboardSaleResposeDTO.getTotalSalesInReal());
        Double mediaByParking = calculateMediaByParking(parkings.size(), dashboardSaleResposeDTO.getTotalSalesInReal());
        dashboardSaleResposeDTO.setMediaSalesByHour(mediaByHour);
        dashboardSaleResposeDTO.setMediaSalesByParking(mediaByParking);

        //Mount parkings
        dashboardSaleResposeDTO.setParkings(mountParkings(parkings, initialDate, finalDate));
        return dashboardSaleResposeDTO;
    }

    private Double calculateMediaByHour(Date initialD, Date finalD, Double totalSalesInReal){
        DecimalFormat decimalFormat = new DecimalFormat("0");
        Calendar initialDate = Calendar.getInstance();
        initialDate.setTime(initialD);
        Calendar finalDate = Calendar.getInstance();
        finalDate.setTime(finalD);
        long difference = finalDate.getTimeInMillis() - initialDate.getTimeInMillis();
        long differenceInHours = difference / (60 * 60 * 1000);
        double differ = Double.parseDouble(decimalFormat.format(differenceInHours));
        return totalSalesInReal / differ;
    }

    private Double calculateMediaByParking(int parkings, Double totalSalesInReal){
        return totalSalesInReal / parkings;
    }

    private List<ParkingDashboardSale>  mountParkings(List<Parking> parkings, Date initialDate, Date finalDate){
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
