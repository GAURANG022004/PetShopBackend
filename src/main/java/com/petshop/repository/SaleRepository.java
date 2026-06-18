package com.petshop.repository;

import com.petshop.entity.Sale;
import com.petshop.entity.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findBySaleNumber(String saleNumber);

    boolean existsBySaleNumber(String saleNumber);

    List<Sale> findByStatus(SaleStatus status);

    @Query("SELECT s FROM Sale s WHERE DATE(s.saleDate) = CURRENT_DATE")
    List<Sale> findTodaySales();

    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = YEAR(CURRENT_DATE) AND MONTH(s.saleDate) = MONTH(CURRENT_DATE)")
    List<Sale> findMonthlySales();

    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    List<Sale> findSalesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(s) FROM Sale s WHERE DATE(s.saleDate) = CURRENT_DATE")
    Long countTodaySales();

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE DATE(s.saleDate) = CURRENT_DATE")
    java.math.BigDecimal sumTodayRevenue();

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE YEAR(s.saleDate) = YEAR(CURRENT_DATE) AND MONTH(s.saleDate) = MONTH(CURRENT_DATE)")
    java.math.BigDecimal sumMonthlyRevenue();

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s")
    java.math.BigDecimal sumTotalRevenue();
}
