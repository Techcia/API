package com.techcia.dtos;

import com.techcia.models.Parking;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ParkingUpdateDTO {

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
    private int numberOfVacancies;
    @NotNull(message = "value per hour are required")
    private double valuePerHour;

    public Parking convertToEntity(Parking parking){

        parking.setName(this.getName());
        parking.setStreet(this.getStreet());
        parking.setPhone(this.getPhone());
        parking.setNumber(this.getNumber());
        parking.setPostalCode(this.getPostalCode());
        parking.setNeighborhood(this.getNeighborhood());
        parking.setCity(this.getCity());
        parking.setState(this.getState());
        parking.setNumberOfVacancies(this.getNumberOfVacancies());
        parking.setCompany(parking.getCompany());
        parking.setValuePerHour(this.valuePerHour);
        return parking;
    }
}
