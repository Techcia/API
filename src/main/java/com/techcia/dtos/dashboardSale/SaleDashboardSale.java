package com.techcia.dtos.dashboardSale;
import com.techcia.models.Sale;
import lombok.Data;

import java.util.Date;

@Data
public class SaleDashboardSale {
    private Double value;
    private Date dataPay;

    public void convertSaleInEntity(Sale sale){
        this.setDataPay(sale.getDataPay());
        this.setValue(sale.getValue());
    }
}
