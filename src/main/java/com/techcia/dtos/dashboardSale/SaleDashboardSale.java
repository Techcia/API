package com.techcia.dtos.dashboardSale;
import com.techcia.models.Sale;
import lombok.Data;

import java.util.Date;

@Data
public class SaleDashboardSale {
    private Double value;
    private Date date;

    public void convertSaleInEntity(Sale sale){
        this.setDate(sale.getDataPay());
        this.setValue(sale.getValue());
    }
}
