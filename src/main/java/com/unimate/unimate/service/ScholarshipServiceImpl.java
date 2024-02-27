package com.unimate.unimate.service;

import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScholarshipServiceImpl implements ScholarshipService{
    @Autowired
    private ScholarshipRepository scholarshipRepository;

    @Override
    public void saveScholarship(Scholarship scholarship) {
        scholarshipRepository.save(scholarship);
    }

    @Override
    public List<Scholarship> getAllScholarship() {
        return scholarshipRepository.findAll();
    }

    @Override
    public Scholarship getScholarshipById(Long id) {
        return scholarshipRepository.findScholarshipById(id);
    }
}
