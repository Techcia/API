package com.techcia.services;

import com.techcia.dtos.dashboardSale.DashboardSaleResposeDTO;
import com.techcia.dtos.dashboardSale.SaleDashboardSale;
import com.techcia.models.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ParkingService parkingService;
    private final SaleService saleService;

    public DashboardSaleResposeDTO dashboardSales(Date initialDate, Date finalDate, List<Integer> parkings){
        DashboardSaleResposeDTO dashboardSaleResposeDTO = new DashboardSaleResposeDTO();

        // Find total and sum value sales and parking
        Map<String, Object> countAndSumValueSales = saleService.countAndSumValueSales(parkings, initialDate, finalDate);
        if(countAndSumValueSales.get("totalSalesInReal") == null || countAndSumValueSales.get("totalSales") == null){
            dashboardSaleResposeDTO.dashboardNull();
            return dashboardSaleResposeDTO;
        }
        dashboardSaleResposeDTO.setTotalSales(Long.parseLong(countAndSumValueSales.get("totalSales").toString()));
        dashboardSaleResposeDTO.setTotalSalesInReal(Double.parseDouble(countAndSumValueSales.get("totalSalesInReal").toString()));

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

    private List<SaleDashboardSale>  mountParkings(List<Integer> parkings, Date initialDate, Date finalDate){
            List<Sale> sales = this.saleService.findByParkingsByDate(parkings, initialDate, finalDate);
            List<SaleDashboardSale> listSaleDashboardSale = new ArrayList<SaleDashboardSale>();
            for (com.techcia.models.Sale sale : sales) {
                SaleDashboardSale saleDashboardSale = new SaleDashboardSale();
                saleDashboardSale.convertSaleInEntity(sale);
                listSaleDashboardSale.add(saleDashboardSale);
            }
        return listSaleDashboardSale;
    }
}
