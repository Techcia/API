package com.techcia.dtos.dashboardSale;
import com.techcia.models.Sale;
import lombok.Data;

import java.util.Date;

@Data
public class SaleDashboardSale {
    private Long id;
    private Double value;
    private String status;
    private Date checkin;
    private Date dataPay;
    private Date checkout;

    public void convertSaleInEntity(Sale sale){
        this.setId(sale.getId());
        this.setCheckin(sale.getCheckin());
        this.setCheckout(sale.getCheckout());
        this.setDataPay(sale.getDataPay());
        this.setStatus(sale.getStatus());
        this.setValue(sale.getValue());
    }
}
