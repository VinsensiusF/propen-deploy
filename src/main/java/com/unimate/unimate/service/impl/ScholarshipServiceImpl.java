package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.ScholarshipResponseDTO;
import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.entity.ScholarshipBenefit;
import com.unimate.unimate.entity.ScholarshipMajor;
import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipStatus;
import com.unimate.unimate.enums.ScholarshipType;
import com.unimate.unimate.repository.ScholarshipBenefitRepository;
import com.unimate.unimate.repository.ScholarshipMajorRepository;
import com.unimate.unimate.repository.ScholarshipRepository;
import com.unimate.unimate.service.ScholarshipService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.ArrayList;
import java.util.Set;

@Service
public class ScholarshipServiceImpl implements ScholarshipService {
    @PersistenceContext
    private EntityManager entityManager;
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
        updateScholarshipStatuses();
        return scholarshipRepository.findAll();
    }

    @Override
    public Scholarship getScholarshipById(Long id) {
        updateScholarshipStatuses();
        return scholarshipRepository.findScholarshipById(id);
    }

    @Override
    public ScholarshipResponseDTO getScholarshipDetailById(Long id) {
        updateScholarshipStatuses();
        Scholarship scholarship = scholarshipRepository.findScholarshipById(id);
        ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
        scholarshipToScholarshipResponseDTO(scholarshipResponseDTO, scholarship);

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
    public List<ScholarshipResponseDTO> getScholarshipsByKeyword(String keyword) {
        updateScholarshipStatuses();
        List<Scholarship> scholarships = scholarshipRepository.findScholarshipsByTitleContainingIgnoreCase(keyword);
        List<ScholarshipResponseDTO> scholarshipResponseDTOS = new ArrayList<>();
        for(Scholarship scholarship : scholarships){
            ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
            scholarshipToScholarshipResponseDTO(scholarshipResponseDTO, scholarship);

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
            scholarshipResponseDTOS.add(scholarshipResponseDTO);
        }
        return scholarshipResponseDTOS;
    }

    @Override
    public Scholarship createScholarship(String title, String university, String description, String standardizedTest, ScholarshipType scholarshipType, Set<ScholarshipDegree> degrees, String minimumGPA, String minimumAge, String languageTest, Date endedAt, Date startedAt, ArrayList<String> benefit, ArrayList<String> major) {
        Scholarship scholarship = new Scholarship();
        scholarship.setTitle(title);
        scholarship.setDescription(description);
        scholarship.setScholarshipType(scholarshipType);
        scholarship.setScholarshipDegrees(degrees);
        scholarship.setMinimumAge(minimumAge);
        scholarship.setMinimumGPA(minimumGPA);
        scholarship.setStartedAt(startedAt);
        scholarship.setEndedAt(endedAt);
        scholarship.setUniversity(university);
        scholarship.setLanguageTest(languageTest);
        scholarship.setStandardizedTest(standardizedTest);
        saveScholarship(scholarship);
        for (String benefitItem : benefit){
            ScholarshipBenefit newBenefit = new ScholarshipBenefit();
            newBenefit.setScholarship(scholarship);
            newBenefit.setBenefit(benefitItem);
            scholarshipBenefitRepository.save(newBenefit);
        }
        for (String majorItem : major){
            ScholarshipMajor newMajor = new ScholarshipMajor();
            newMajor.setScholarship(scholarship);
            newMajor.setMajor(majorItem);
            scholarshipMajorRepository.save(newMajor);
        }
        updateScholarshipStatuses();
        return scholarship;
    }

    @Override
    public Scholarship updateScholarship(String title, String university, String description, String standardizedTest, ScholarshipType scholarshipType, Set<ScholarshipDegree> degrees, String minimumGPA, String minimumAge, String languageTest, Date endedAt, Date startedAt, ArrayList<String> benefit, ArrayList<String> major, long scholarshipId) {
        Scholarship scholarship = scholarshipRepository.findScholarshipById(scholarshipId);
        scholarship.setTitle(title);
        scholarship.setDescription(description);
        scholarship.setScholarshipType(scholarshipType);
        scholarship.setScholarshipDegrees(degrees);
        scholarship.setMinimumAge(minimumAge);
        scholarship.setMinimumGPA(minimumGPA);
        scholarship.setStartedAt(startedAt);
        scholarship.setEndedAt(endedAt);
        scholarship.setUniversity(university);
        scholarship.setLanguageTest(languageTest);
        scholarship.setStandardizedTest(standardizedTest);
        scholarshipBenefitRepository.deleteAll(scholarshipBenefitRepository.findScholarshipBenefitsByScholarship(scholarship));
        scholarshipMajorRepository.deleteAll(scholarshipMajorRepository.findScholarshipMajorsByScholarship(scholarship));
        saveScholarship(scholarship);
        for (String benefitItem : benefit){
            ScholarshipBenefit newBenefit = new ScholarshipBenefit();
            newBenefit.setScholarship(scholarship);
            newBenefit.setBenefit(benefitItem);
            scholarshipBenefitRepository.save(newBenefit);
        }
        for (String majorItem : major){
            ScholarshipMajor newMajor = new ScholarshipMajor();
            newMajor.setScholarship(scholarship);
            newMajor.setMajor(majorItem);
            scholarshipMajorRepository.save(newMajor);
        }
        updateScholarshipStatuses();
        return scholarship;
    }

    private void updateScholarshipStatuses() {
        List<Scholarship> scholarships = scholarshipRepository.findAll();
        Date currentDate = new Date();
        for (Scholarship scholarship : scholarships) {
            if(scholarship.getScholarshipStatus() == null && currentDate.after(scholarship.getEndedAt())){
                scholarship.setScholarshipStatus(ScholarshipStatus.CLOSED);
                scholarshipRepository.save(scholarship);
            }
            if(scholarship.getScholarshipStatus() == null && currentDate.before(scholarship.getEndedAt())){
                scholarship.setScholarshipStatus(ScholarshipStatus.OPEN);
                scholarshipRepository.save(scholarship);
            }
            // If the scholarship is not already closed and today's date is after endedAt date
            if (scholarship.getScholarshipStatus() != ScholarshipStatus.CLOSED && currentDate.after(scholarship.getEndedAt())) {
                scholarship.setScholarshipStatus(ScholarshipStatus.CLOSED);
                scholarshipRepository.save(scholarship); // Update the scholarship status
            }
            // the endDate is extended case
            if (scholarship.getScholarshipStatus() == ScholarshipStatus.CLOSED && currentDate.before(scholarship.getEndedAt())) {
                scholarship.setScholarshipStatus(ScholarshipStatus.OPEN);
                scholarshipRepository.save(scholarship); // Update the scholarship status
            }
        }
    }

    private void scholarshipToScholarshipResponseDTO(ScholarshipResponseDTO scholarshipResponseDTO, Scholarship scholarship) {
        scholarshipResponseDTO.setScholarshipId(scholarship.getId());
        scholarshipResponseDTO.setTitle(scholarship.getTitle());
        scholarshipResponseDTO.setDescription(scholarship.getDescription());
        scholarshipResponseDTO.setScholarshipType(scholarship.getScholarshipType());
        scholarshipResponseDTO.setScholarshipStatus(scholarship.getScholarshipStatus());
        scholarshipResponseDTO.setScholarshipDegrees(scholarship.getScholarshipDegrees());
        scholarshipResponseDTO.setMinimumAge(scholarship.getMinimumAge());
        scholarshipResponseDTO.setMinimumGPA(scholarship.getMinimumGPA());
        scholarshipResponseDTO.setStartedAt(scholarship.getStartedAt());
        scholarshipResponseDTO.setEndedAt(scholarship.getEndedAt());
        scholarshipResponseDTO.setUniversity(scholarship.getUniversity());
        scholarshipResponseDTO.setLanguageTest(scholarship.getLanguageTest());
        scholarshipResponseDTO.setStandardizedTest(scholarship.getStandardizedTest());
    }

    @Override
    public List<ScholarshipResponseDTO> getAllScholarshipByOpeningMonth(int month) {
        updateScholarshipStatuses();
        List<Scholarship> scholarships = scholarshipRepository.findScholarshipsByOpeningMonth(month);
        List<ScholarshipResponseDTO> responseDTOS = new ArrayList<>();
        for(Scholarship scholarship : scholarships){
            ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
            scholarshipToScholarshipResponseDTO(scholarshipResponseDTO, scholarship);

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
            responseDTOS.add(scholarshipResponseDTO);
        }

        return responseDTOS;
    }

    @Override
    public List<Scholarship> getScholarshipsByFilters(ScholarshipDegree degreeFilter, ScholarshipType fundFilter, String sortByOpeningDate, String keyword) {
        updateScholarshipStatuses();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Scholarship> criteriaQuery = criteriaBuilder.createQuery(Scholarship.class);
        Root<Scholarship> scholarshipRoot = criteriaQuery.from(Scholarship.class);
        List<Predicate> predicates = new ArrayList<>();

        // Add degree filter if provided
        if (degreeFilter != null) {
            // Join for scholarshipDegrees
            Join<Scholarship, ScholarshipDegree> degreeJoin = scholarshipRoot.join("scholarshipDegrees", JoinType.INNER);

            // Define predicate for degree filter
            Predicate degreePredicate = criteriaBuilder.equal(degreeJoin, degreeFilter);
            predicates.add(degreePredicate);
        }

        if(fundFilter != null) {
            // Add fund filter
            Predicate fundPredicate = criteriaBuilder.equal(scholarshipRoot.get("scholarshipType"), fundFilter);
            predicates.add(fundPredicate);
        }

        // Add filter for keyword if provided
        if (keyword != null && !keyword.isEmpty()) {
            // Define predicate for keyword filter
            Predicate keywordPredicate = criteriaBuilder.like(criteriaBuilder.lower(scholarshipRoot.get("title")), "%" + keyword.toLowerCase() + "%");
            predicates.add(keywordPredicate);
        }

        // Combine predicates with AND
        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(finalPredicate);

        // Order by startedAt
        if ("asc".equalsIgnoreCase(sortByOpeningDate)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(scholarshipRoot.get("startedAt")));
        } else if ("desc".equalsIgnoreCase(sortByOpeningDate)) {
            criteriaQuery.orderBy(criteriaBuilder.desc(scholarshipRoot.get("startedAt")));
        }

        // Execute query and return results
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    @Override
    public Long getCountScholarship() {
        return scholarshipRepository.countAllScholarship();
    }
}
