package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.entity.ScholarshipMajor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ScholarshipMajorRepository extends JpaRepository<ScholarshipMajor, Long> {
    ArrayList<ScholarshipMajor> findScholarshipMajorsByScholarship(Scholarship scholarship);
}
