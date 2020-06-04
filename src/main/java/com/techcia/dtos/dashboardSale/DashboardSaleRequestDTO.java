package com.techcia.dtos.dashboardSale;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class DashboardSaleRequestDTO {
    @NotNull(message = "Initial date is required")
    private String initialDate;
    @NotNull(message = "Final date is required")
    private String finalDate;
    @NotNull(message = "Parkings is required")
    private Integer[] parkings;
}
