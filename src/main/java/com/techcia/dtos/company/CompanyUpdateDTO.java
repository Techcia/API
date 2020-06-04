package com.techcia.dtos.company;

import com.techcia.models.Company;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class CompanyUpdateDTO {

    @NotNull(message = "Document is required")
    private String document;
    @NotNull(message = "Email is required")
    @Email(message = "Email needs to be valid")
    private String email;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Trade Name is required")
    private String tradeName;

    public Company convertToEntity(Company company){

        company.setDocument(this.getDocument());
        company.setEmail(this.getEmail());
        company.setName(this.getName());
        company.setTradeName(this.getTradeName());

        return company;
    }
}
