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

    @Query(value = "SELECT id, s.value as 'value', s.data_pay, s.checkin, s.checkout, s.client_id, s.parking_id, s.status FROM sale s\n" +
            "WHERE parking_id = :parkingId AND s.checkin >= :initialDate AND s.checkout <= :finalDate", nativeQuery = true)
    List<Sale> findByParkingByDate(@Param("parkingId") Long parkingId, @Param("initialDate") Date initialDate,  @Param("finalDate") Date finalDate);
}
