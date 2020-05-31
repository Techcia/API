package com.techcia.repositories;

import com.techcia.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository  extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
