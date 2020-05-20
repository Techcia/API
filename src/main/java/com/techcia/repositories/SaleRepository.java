package com.techcia.repositories;

import com.techcia.models.Client;
import com.techcia.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);

    List<Sale> findByClient(Client client);
}
