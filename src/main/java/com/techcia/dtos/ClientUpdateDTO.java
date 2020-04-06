package com.techcia.dtos;

import com.techcia.models.Client;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClientUpdateDTO {
    @NotNull(message = "Document is required")
    private String document;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Type document is required")
    private String typeDocument;
    @NotNull(message = "Email is required")
    @Email(message = "Email needs to be valid")
    private String email;

    public Client convertToEntity(Client client){
        client.setName(this.getName());
        client.setEmail(this.getEmail());
        client.setDocument(this.getDocument());
        client.setTypeDocument(this.typeDocument);
        return  client;
    }
}