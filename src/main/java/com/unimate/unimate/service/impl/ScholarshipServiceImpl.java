package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.ScholarshipDTO;
import com.unimate.unimate.dto.ScholarshipResponseDTO;
import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.entity.ScholarshipBenefit;
import com.unimate.unimate.entity.ScholarshipMajor;
import com.unimate.unimate.repository.ScholarshipBenefitRepository;
import com.unimate.unimate.repository.ScholarshipMajorRepository;
import com.unimate.unimate.repository.ScholarshipRepository;
import com.unimate.unimate.service.ScholarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScholarshipServiceImpl implements ScholarshipService {
    private final ScholarshipRepository scholarshipRepository;
    private final ScholarshipMajorRepository scholarshipMajorRepository;

    private final ScholarshipBenefitRepository scholarshipBenefitRepository;

    @Autowired
    public ScholarshipServiceImpl(ScholarshipRepository scholarshipRepository, ScholarshipBenefitRepository scholarshipBenefitRepository, ScholarshipMajorRepository scholarshipMajorRepository){
        this.scholarshipRepository = scholarshipRepository;
        this.scholarshipMajorRepository = scholarshipMajorRepository;
        this.scholarshipBenefitRepository = scholarshipBenefitRepository;
    }

    @Override
    public void saveScholarship(Scholarship scholarship) {
        scholarshipRepository.save(scholarship);
    }

    @Override
    public void deleteScholarship(Scholarship scholarship) {
        ArrayList<ScholarshipMajor> majors = scholarshipMajorRepository.findScholarshipMajorsByScholarship(scholarship);
        ArrayList<ScholarshipBenefit> benefits = scholarshipBenefitRepository.findScholarshipBenefitsByScholarship(scholarship);

        scholarshipMajorRepository.deleteAll(majors);
        scholarshipBenefitRepository.deleteAll(benefits);
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
    public ScholarshipResponseDTO getScholarshipDetailById(Long id) {
        Scholarship scholarship = scholarshipRepository.findScholarshipById(id);
        ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
        scholarshipResponseDTO.setName(scholarship.getName());
        scholarshipResponseDTO.setDescription(scholarship.getDescription());
        scholarshipResponseDTO.setScholarshipType(scholarship.getScholarshipType());
        scholarshipResponseDTO.setScholarshipStatus(scholarship.getScholarshipStatus());
        scholarshipResponseDTO.setScholarshipDegrees(scholarship.getScholarshipDegrees());
        scholarshipResponseDTO.setMinimalAge(scholarship.getMinimalAge());
        scholarshipResponseDTO.setIpkMinimal(scholarship.getIpkMinimal());
        scholarshipResponseDTO.setStartedAt(scholarship.getStartedAt());
        scholarshipResponseDTO.setEndedAt(scholarship.getEndedAt());
        scholarshipResponseDTO.setUniversity(scholarship.getUniversity());

        ArrayList<ScholarshipBenefit> benefits = scholarshipBenefitRepository.findScholarshipBenefitsByScholarship(scholarship);
        ArrayList<String> benefitItems = new ArrayList<>();
        for (ScholarshipBenefit benefit : benefits){
            benefitItems.add(benefit.getBenefit());
        }

        ArrayList<ScholarshipMajor> majors = scholarshipMajorRepository.findScholarshipMajorsByScholarship(scholarship);
        ArrayList<String> majorItems = new ArrayList<>();
        for (ScholarshipMajor major : majors){
            majorItems.add(major.getMajor());
        }

        scholarshipResponseDTO.setBenefit(benefitItems);
        scholarshipResponseDTO.setMajor(majorItems);
        return scholarshipResponseDTO;
    }

    @Override
    public Scholarship createScholarship(ScholarshipDTO scholarshipDTO) {
        Scholarship scholarship = new Scholarship();
        scholarship.setName(scholarshipDTO.getName());
        scholarship.setDescription(scholarshipDTO.getDescription());
        scholarship.setScholarshipType(scholarshipDTO.getScholarshipType());
        scholarship.setScholarshipStatus(scholarshipDTO.getScholarshipStatus());
        scholarship.setScholarshipDegrees(scholarshipDTO.getScholarshipDegrees());
        scholarship.setMinimalAge(scholarshipDTO.getMinimalAge());
        scholarship.setIpkMinimal(scholarshipDTO.getIpkMinimal());
        scholarship.setStartedAt(scholarshipDTO.getStartedAt());
        scholarship.setEndedAt(scholarshipDTO.getEndedAt());
        scholarship.setUniversity(scholarshipDTO.getUniversity());
        saveScholarship(scholarship);
        for (String benefitItem : scholarshipDTO.getBenefit()){
            ScholarshipBenefit benefit = new ScholarshipBenefit();
            benefit.setScholarship(scholarship);
            benefit.setBenefit(benefitItem);
            scholarshipBenefitRepository.save(benefit);
        }
        for (String majorItem : scholarshipDTO.getMajor()){
            ScholarshipMajor major = new ScholarshipMajor();
            major.setScholarship(scholarship);
            major.setMajor(majorItem);
            scholarshipMajorRepository.save(major);
        }
        return scholarship;
    }

    @Override
    public Scholarship updateScholarship(ScholarshipDTO scholarshipDTO, long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findScholarshipById(scholarshipId);
        scholarship.setName(scholarshipDTO.getName());
        scholarship.setDescription(scholarshipDTO.getDescription());
        scholarship.setScholarshipType(scholarshipDTO.getScholarshipType());
        scholarship.setScholarshipStatus(scholarshipDTO.getScholarshipStatus());
        scholarship.setScholarshipDegrees(scholarshipDTO.getScholarshipDegrees());
        scholarship.setMinimalAge(scholarshipDTO.getMinimalAge());
        scholarship.setIpkMinimal(scholarshipDTO.getIpkMinimal());
        scholarship.setStartedAt(scholarshipDTO.getStartedAt());
        scholarship.setEndedAt(scholarshipDTO.getEndedAt());
        scholarship.setUniversity(scholarshipDTO.getUniversity());
        scholarshipBenefitRepository.deleteAll(scholarshipBenefitRepository.findScholarshipBenefitsByScholarship(scholarship));
        scholarshipMajorRepository.deleteAll(scholarshipMajorRepository.findScholarshipMajorsByScholarship(scholarship));
        saveScholarship(scholarship);
        for (String benefitItem : scholarshipDTO.getBenefit()){
            ScholarshipBenefit benefit = new ScholarshipBenefit();
            benefit.setScholarship(scholarship);
            benefit.setBenefit(benefitItem);
            scholarshipBenefitRepository.save(benefit);
        }
        for (String majorItem : scholarshipDTO.getMajor()){
            ScholarshipMajor major = new ScholarshipMajor();
            major.setScholarship(scholarship);
            major.setMajor(majorItem);
            scholarshipMajorRepository.save(major);
        }
        return scholarship;
    }
}
