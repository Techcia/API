package com.techcia.repositories;

import com.techcia.models.Client;
import com.techcia.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository <Company, Long> {
    Optional<Company> findByEmail(String email);
}
