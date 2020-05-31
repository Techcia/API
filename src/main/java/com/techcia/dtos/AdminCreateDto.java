package com.techcia.dtos;

import com.techcia.models.Admin;
import com.techcia.models.Client;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

@Data
public class AdminCreateDto {
    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;

    public Admin convertToEntity(){
        Admin admin = new Admin();
        admin.setUsername(this.getUsername());
        admin.setPassword(new BCryptPasswordEncoder().encode(this.getPassword()));
        return  admin;
    }
}
