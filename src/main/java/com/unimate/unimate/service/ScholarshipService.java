package com.unimate.unimate.service;

import com.unimate.unimate.dto.ScholarshipDTO;
import com.unimate.unimate.dto.ScholarshipResponseDTO;
import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ScholarshipService {
    void saveScholarship(Scholarship scholarship);

    void deleteScholarship(Scholarship scholarship);

    List<Scholarship> getAllScholarship();

    Scholarship getScholarshipById(Long id);

    ScholarshipResponseDTO getScholarshipDetailById(Long id);

    List<ScholarshipResponseDTO> getScholarshipsByKeyword(String keyword);

    Scholarship createScholarship(String title,
                                  String university,
                                  String description,
                                  String standardizedTest,
                                  ScholarshipType scholarshipType,
                                  Set<ScholarshipDegree> degrees,
                                  String minimumGPA,
                                  String minimumAge,
                                  String languageTest,
                                  Date endedAt,
                                  Date startedAt,
                                  ArrayList<String> benefit,
                                  ArrayList<String> major);

    Scholarship updateScholarship(String title,
                                  String university,
                                  String description,
                                  String standardizedTest,
                                  ScholarshipType scholarshipType,
                                  Set<ScholarshipDegree> degrees,
                                  String minimumGPA,
                                  String minimumAge,
                                  String languageTest,
                                  Date endedAt,
                                  Date startedAt,
                                  ArrayList<String> benefit,
                                  ArrayList<String> major,
                                  long scholarshipId);

    List<ScholarshipResponseDTO> getAllScholarshipByOpeningMonth(int month);

    List<Scholarship> getScholarshipsByFilters(ScholarshipDegree degreeFilter, ScholarshipType fundFilter, String sortByOpeningDate, String keyword);
}
