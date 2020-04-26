package com.techcia.dtos;

import com.techcia.models.Sale;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SaleCreateDTO {

    @NotNull(message = "Value is required")
    private double value;

    public Sale convertToEntity(){

        Sale sale = new Sale();
        sale.setValue(this.getValue());

        return sale;
    }
}
