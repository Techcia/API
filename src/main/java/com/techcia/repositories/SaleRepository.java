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
            "INNER JOIN parking p on p.id = s.parking_id\n" +
            "INNER JOIN company c on c.id = p.company_id\n" +
            "where c.id = :companyId AND s.checkin >= :initialDate AND s.checkout <= :finalDate", nativeQuery = true)
    Map<String, Object> countAndSumValueSales(@Param("companyId") Long companyId, @Param("initialDate") Date initialDate, @Param("finalDate") Date finalDate);

    @Query(value = "SELECT id, sum(s.value) as 'value', s.data_pay, s.checkin, s.checkout, s.client_id, s.parking_id, s.status FROM sale s\n" +
            "WHERE parking_id = :parkingId AND s.checkin >= :initialDate AND s.checkout <= :finalDate\n" +
            "group by year(s.data_pay), month(s.data_pay), day(data_pay)", nativeQuery = true)
    List<Sale> findByParkingByDate(@Param("parkingId") Long parkingId, @Param("initialDate") Date initialDate,  @Param("finalDate") Date finalDate);
}
