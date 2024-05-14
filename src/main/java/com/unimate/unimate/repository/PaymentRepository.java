package com.unimate.unimate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;


import com.unimate.unimate.entity.Payment;

import jakarta.transaction.Transactional;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

@Transactional
public interface PaymentRepository extends JpaRepository<Payment,UUID>  {

    List<Payment> findPaymentBySiswaId(Long siswaId);
    Optional<Payment> findById(UUID id);
    @Query(value = "SELECT e FROM Payment e WHERE DATE(e.date) = DATE(:dateParam) OR DATE(e.payat) = DATE(:dateParam)")
    List<Payment> findAllByDateOrPayat(@Param("dateParam") Date date);
    @Query("SELECT p FROM Payment p WHERE p.payat BETWEEN :startDate AND :endDate OR p.date BETWEEN :startDate AND :endDate")
    List<Payment> findAllByPayatOrDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query(value = "SELECT SUM(e.price) FROM Payment e WHERE  DATE(e.payat) = DATE(:dateParam)")
    Long countByDate(@Param("dateParam") Date dateParam);
    @Query(value = "SELECT SUM(e.net) FROM Payment e WHERE  DATE(e.payat) = DATE(:dateParam)")
    Long countByDate2(@Param("dateParam") Date dateParam);
    List<Payment> findBySiswaIdAndCourseIdAndStatusIn(Long siswaId, Long courseId, List<String> statuses);
    @Query(value = "SELECT SUM(e.price) FROM Payment e WHERE DATE(e.payat) BETWEEN DATE(:startDate) AND DATE(:endDate)")
Long countByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

@Query(value = "SELECT SUM(e.net) FROM Payment e WHERE DATE(e.payat) BETWEEN DATE(:startDate) AND DATE(:endDate)")
Long countByDateRange2(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
