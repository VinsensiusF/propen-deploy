package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.entity.ScholarshipBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ScholarshipBenefitRepository extends JpaRepository<ScholarshipBenefit, Long> {
    ArrayList<ScholarshipBenefit> findScholarshipBenefitsByScholarship(Scholarship scholarship);
}
