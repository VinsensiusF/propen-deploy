package com.unimate.unimate.service;

import com.unimate.unimate.entity.Scholarship;

import java.util.Date;
import java.util.List;

public interface ScholarshipService {
    void saveScholarship(Scholarship scholarship);

    void deleteScholarship(Scholarship scholarship);

    List<Scholarship> getAllScholarship();

    Scholarship getScholarshipById(Long id);

    Scholarship createScholarship(String name, String description, Date startedAt, Date endedAt);
}
