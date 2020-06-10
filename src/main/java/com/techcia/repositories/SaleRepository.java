package com.techcia.repositories;

import com.techcia.models.Client;
import com.techcia.models.Company;
import com.techcia.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);

    List<Sale> findByClientOrderByIdDesc(Client client);



    @Query(value = "SELECT sum(value) as 'totalSalesInReal', count(*) as 'totalSales' FROM sale s\n" +
            "where s.parking_id in (:parkings) AND s.checkin >= :initialDate AND s.checkout <= :finalDate", nativeQuery = true)
    Map<String, Object> countAndSumValueSales(@Param("parkings") List<Integer> parkings, @Param("initialDate") Date initialDate, @Param("finalDate") Date finalDate);

    @Query(value = "SELECT id, sum(s.value) as 'value', s.data_pay, s.checkin, s.checkout, s.client_id, s.parking_id, s.status FROM sale s\n" +
            "WHERE parking_id in (:parkings) AND s.checkin >= :initialDate AND s.checkout <= :finalDate\n" +
            "group by year(s.data_pay), month(s.data_pay), day(data_pay)", nativeQuery = true)
    List<Sale> findByParkingsByDate(@Param("parkings") List<Integer> parkings, @Param("initialDate") Date initialDate,  @Param("finalDate") Date finalDate);



    @Query(value = "SELECT s.* FROM sale s\n" +
            "INNER JOIN parking p ON p.id = s.parking_id\n" +
            "INNER JOIN company c ON c.id = p.company_id\n" +
            "INNER JOIN client cli ON cli.id = s.client_id\n" +
            "WHERE c.id = :companyId AND s.checkin >= :initialDate\n" +
            "AND (s.checkout <= :endDate OR s.checkout IS NULL) AND p.id IN (:parkings) AND cli.name like %:nameClient% ORDER BY s.id DESC \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM sale s\n" +
                    "INNER JOIN parking p ON p.id = s.parking_id\n" +
                    "INNER JOIN company c ON c.id = p.company_id\n" +
                    "INNER JOIN client cli ON cli.id = s.client_id\n" +
                    "WHERE c.id = :companyId AND s.checkin >= :initialDate\n" +
                    "AND (s.checkout <= :endDate OR s.checkout IS NULL)  AND p.id IN (:parkings) AND cli.name like %:nameClient%",
            nativeQuery = true)
    Page<Sale> searchSale(
            @Param("nameClient") String nameClient,
            @Param("parkings") List<String> parkings,
            @Param("companyId") Long companyId,
            @Param("initialDate") Date initialDate,
            @Param("endDate") Date endDate,
            Pageable pageable);
}
