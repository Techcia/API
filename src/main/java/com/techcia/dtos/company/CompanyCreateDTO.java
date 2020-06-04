package com.techcia.dtos.company;

import com.techcia.models.Company;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CompanyCreateDTO {

    @NotNull(message = "Document is required")
    private String document;
    @NotNull(message = "Email is required")
    @Email(message = "Email needs to be valid")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Trade Name is required")
    private String tradeName;

    public Company convertToEntity(){

        Company company = new Company();
        company.setDocument(this.getDocument());
        company.setEmail(this.getEmail());
        company.setPassword(new BCryptPasswordEncoder().encode(this.getPassword()));
        company.setName(this.getName());
        company.setTradeName(this.getTradeName());

        return company;
    }
}
