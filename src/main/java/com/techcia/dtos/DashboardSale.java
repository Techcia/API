package com.techcia.dtos;
import com.techcia.models.Sale;
import lombok.Data;

import java.util.Date;

@Data
public class DashboardSale {
    private Long id;
    private Double value;
    private String status;
    private Date checkin;
    private Date dataPay;
    private Date checkout;
}
