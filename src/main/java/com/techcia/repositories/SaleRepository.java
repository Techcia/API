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
import java.util.Map;
import java.util.Optional;
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);

    List<Sale> findByClientOrderByIdDesc(Client client);

    @Query(value = "SELECT sum(value) as 'totalSalesInReal', count(*) as 'totalSales' FROM sale s\n" +
            "where s.parking_id in (:parkings) AND s.checkin >= :initialDate AND s.checkout <= :finalDate", nativeQuery = true)
    Map<String, Object> countAndSumValueSales(@Param("parkings") String parkings, @Param("initialDate") Date initialDate, @Param("finalDate") Date finalDate);

    @Query(value = "SELECT id, sum(s.value) as 'value', s.data_pay, s.checkin, s.checkout, s.client_id, s.parking_id, s.status FROM sale s\n" +
            "WHERE parking_id in (:parkings) AND s.checkin >= :initialDate AND s.checkout <= :finalDate\n" +
            "group by year(s.data_pay), month(s.data_pay), day(data_pay)", nativeQuery = true)
    List<Sale> findByParkingsByDate(@Param("parkings") String parkings, @Param("initialDate") Date initialDate,  @Param("finalDate") Date finalDate);
}
