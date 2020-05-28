package com.techcia.repositories;

import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);

    List<Sale> findByClient(Client client);

    @Query(value = "SELECT s.* FROM sale s\n" +
            "INNER JOIN parking p ON p.id = s.parking_id\n" +
            "INNER JOIN company c ON c.id = p.company_id\n" +
            "WHERE c.id = :companyId AND s.checkin >= :initialDate AND s.checkout <= :finalDate", nativeQuery = true)
    List<Sale> findByCompanyByDate(@Param("companyId") Long companyId, @Param("initialDate") Date initialDate,  @Param("finalDate") Date finalDate);
}
