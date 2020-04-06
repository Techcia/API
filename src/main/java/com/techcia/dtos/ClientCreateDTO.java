package com.techcia.dtos;

import com.techcia.models.Client;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClientCreateDTO {
    @NotNull(message = "Document is required")
    private String document;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Type document is required")
    private String typeDocument;
    @NotNull(message = "Email is required")
    @Email(message = "Email needs to be valid")
    private String email;
    @NotNull(message = "Password is required")
    private String password;

    public Client convertToEntity(){
        Client client = new Client();
        client.setName(this.getName());
        client.setEmail(this.getEmail());
        client.setDocument(this.getDocument());
        client.setTypeDocument(this.typeDocument);
        client.setPassword(new BCryptPasswordEncoder().encode(this.getPassword()));
        return  client;
    }
}

