package com.techcia.repositories;

import com.techcia.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository  extends JpaRepository<Client, Long> {
    Optional<Client>  findByEmail(String email);
}
