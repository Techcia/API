package com.techcia.dtos;

import com.techcia.models.Company;
import com.techcia.models.Parking;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ParkingCreateDTO {

    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Street is required")
    private String street;
    @NotNull(message = "Phone is required")
    private String phone;
    @NotNull(message = "Number is required")
    private int number;
    @NotNull(message = "Postal Code is required")
    private String postalCode;
    @NotNull(message = "Neighborhood is required")
    private String neighborhood;
    @NotNull(message = "City is required")
    private String city;
    @NotNull(message = "State is required")
    private String state;
    @NotNull(message = "Number of vacancies are required")
    @Min(1)
    private int numberOfVacancies;
    @NotNull(message = "value per hour are required")
    private double valuePerHour;

    public Parking convertToEntity(Company company){
        Parking parking = new Parking();
        parking.setName(this.getName());
        parking.setPhone(this.getPhone());
        parking.setStreet(this.getStreet());
        parking.setNumber(this.getNumber());
        parking.setPostalCode(this.getPostalCode());
        parking.setNeighborhood(this.getNeighborhood());
        parking.setCity(this.getCity());
        parking.setState(this.getState());
        parking.setNumberOfVacancies(this.getNumberOfVacancies());
        parking.setCompany(company);
        parking.setValuePerHour(this.valuePerHour);
        return parking;
    }
}
