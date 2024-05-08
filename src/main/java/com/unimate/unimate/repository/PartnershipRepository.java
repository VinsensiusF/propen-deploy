package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Partnership;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    Partnership findPartnershipById(Long id);
    Partnership findPartnershipByUser(Account user);

    @Query("SELECT MONTH(p.createdAt) AS month, COUNT(p) AS requestCount FROM Partnership p WHERE YEAR(p.createdAt) = :year GROUP BY MONTH(p.createdAt)")
    List<Object[]> countMonthlyRequests(@Param("year") int year);

    @Query("SELECT DAY(p.createdAt) AS day, COUNT(p) AS requestCount FROM Partnership p WHERE YEAR(p.createdAt) = :year AND MONTH(p.createdAt) = :month GROUP BY DAY(p.createdAt)")
    List<Object[]> countDailyRequests(@Param("year") int year, @Param("month") int month);


    @Query("SELECT MONTH(p.createdAt) AS month, COUNT(p) AS requestCount FROM Partnership p GROUP BY MONTH(p.createdAt)")
    List<Object[]> countMonthlyRequestsForAllYears();


    @Query("SELECT p.status, COUNT(p) FROM Partnership p GROUP BY p.status")
    List<Object[]> countRequestsByStatusForAllYearsAndMonths();

    @Query("SELECT p.status, COUNT(p) FROM Partnership p WHERE YEAR(p.createdAt) = :year GROUP BY p.status")
    List<Object[]> countRequestsByStatusForAllMonths(@Param("year") int year);

    @Query("SELECT p.status, COUNT(p) FROM Partnership p WHERE MONTH(p.createdAt) = :month GROUP BY p.status")
    List<Object[]> countRequestsByStatusForAllYears(@Param("month") int month);

    @Query("SELECT p.status, COUNT(p) FROM Partnership p WHERE YEAR(p.createdAt) = :year AND MONTH(p.createdAt) = :month GROUP BY p.status")
    List<Object[]> countRequestsByStatus(@Param("year") int year, @Param("month") int month);










}
