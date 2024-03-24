package com.unimate.unimate.service;

import com.unimate.unimate.dto.ScholarshipDTO;
import com.unimate.unimate.dto.ScholarshipResponseDTO;
import com.unimate.unimate.entity.Scholarship;

import java.util.List;

public interface ScholarshipService {
    void saveScholarship(Scholarship scholarship);

    void deleteScholarship(Scholarship scholarship);

    List<Scholarship> getAllScholarship();

    Scholarship getScholarshipById(Long id);

    ScholarshipResponseDTO getScholarshipDetailById(Long id);

    Scholarship createScholarship(ScholarshipDTO scholarshipDTO);

    Scholarship updateScholarship(ScholarshipDTO scholarshipDTO, long scholarshipId);
}
