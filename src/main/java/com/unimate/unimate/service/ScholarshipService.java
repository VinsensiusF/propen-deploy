package com.unimate.unimate.service;

import com.unimate.unimate.entity.Scholarship;

import java.util.List;

public interface ScholarshipService {
    void saveScholarship(Scholarship scholarship);

    List<Scholarship> getAllScholarship();

    Scholarship getScholarshipById(Long id);
}
