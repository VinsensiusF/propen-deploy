package com.unimate.unimate.service.impl;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.repository.ScholarshipRepository;
import com.unimate.unimate.service.ScholarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScholarshipServiceImpl implements ScholarshipService {
    private final ScholarshipRepository scholarshipRepository;

    @Autowired
    public ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository){
        this.scholarshipRepository = scholarshipRepository;
    }

    @Override
    public void saveScholarship(Scholarship scholarship) {
        scholarshipRepository.save(scholarship);
    }

    @Override
    public void deleteScholarship(Scholarship scholarship) {
        scholarshipRepository.delete(scholarship);
    }

    @Override
    public List<Scholarship> getAllScholarship() {
        return scholarshipRepository.findAll();
    }

    @Override
    public Scholarship getScholarshipById(Long id) {
        return scholarshipRepository.findScholarshipById(id);
    }

    @Override
    public Scholarship createScholarship(String name, String description, Date startedAt, Date endedAt) {
        Scholarship scholarship = new Scholarship();
        scholarship.setName(name);
        scholarship.setDescription(description);
        scholarship.setStartedAt(startedAt);
        scholarship.setEndedAt(endedAt);
        saveScholarship(scholarship);
        return scholarship;
    }
}
