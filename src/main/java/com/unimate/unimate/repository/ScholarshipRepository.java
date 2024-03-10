package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    Scholarship findScholarshipById(Long id);
}
