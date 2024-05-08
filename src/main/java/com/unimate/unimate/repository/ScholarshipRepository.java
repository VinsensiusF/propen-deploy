package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    Scholarship findScholarshipById(Long id);

    @Query("SELECT s FROM Scholarship s WHERE MONTH(s.startedAt) = :month")
    List<Scholarship> findScholarshipsByOpeningMonth(@Param("month") int month);

    List<Scholarship> findScholarshipsByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT COUNT(e) FROM Scholarship e")
    Long countAllScholarship();
}
