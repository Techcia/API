package com.techcia.dtos.sales;

import com.techcia.constants.SaleConstants;
import com.techcia.models.Client;
import com.techcia.models.Parking;
import com.techcia.models.Sale;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SaleCheckinDTO {

    @NotNull(message = "Parkinkg id is required")
    private Long parkingId;

    public Sale convertToEntity(Parking parking){
        Sale sale = new Sale();
        sale.setParking(parking);
        sale.setStatus(SaleConstants.ABERTO);
        return sale;
    }
}
