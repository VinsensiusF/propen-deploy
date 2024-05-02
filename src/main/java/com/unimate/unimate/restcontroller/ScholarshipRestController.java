package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.ScholarshipDTO;
import com.unimate.unimate.dto.ScholarshipResponseDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Scholarship;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipType;
import com.unimate.unimate.service.ScholarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/scholarship")
public class ScholarshipRestController {
    private final ScholarshipService scholarshipService;

    @Autowired
    public ScholarshipRestController(ScholarshipService scholarshipService){
        this.scholarshipService = scholarshipService;
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> createScholarship(@RequestParam String title,
                                                    @RequestParam String university,
                                                    @RequestParam String description,
                                                    @RequestParam String standardizedTest,
                                                    @RequestParam ScholarshipType scholarshipType,
                                                    @RequestParam Set<ScholarshipDegree> degrees,
                                                    @RequestParam String minimumGPA,
                                                    @RequestParam String minimumAge,
                                                    @RequestParam String languageTest,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endedAt,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startedAt,
                                                    @RequestParam ArrayList<String> benefit,
                                                    @RequestParam ArrayList<String> major){
        Scholarship scholarship = scholarshipService.createScholarship(title,
                university,
                description,
                standardizedTest,
                scholarshipType,
                degrees,
                minimumGPA,
                minimumAge,
                languageTest,
                endedAt,
                startedAt,
                benefit,
                major);
        return ResponseEntity.status(HttpStatus.OK).body("Scholarship has been created successfully.");
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> updateScholarship(@RequestParam long scholarshipId,
                                                    @RequestParam String title,
                                                    @RequestParam String university,
                                                    @RequestParam String description,
                                                    @RequestParam String standardizedTest,
                                                    @RequestParam ScholarshipType scholarshipType,
                                                    @RequestParam Set<ScholarshipDegree> degrees,
                                                    @RequestParam String minimumGPA,
                                                    @RequestParam String minimumAge,
                                                    @RequestParam String languageTest,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endedAt,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startedAt,
                                                    @RequestParam ArrayList<String> benefit,
                                                    @RequestParam ArrayList<String> major){
        Scholarship scholarship = scholarshipService.updateScholarship(title,
                university,
                description,
                standardizedTest,
                scholarshipType,
                degrees,
                minimumGPA,
                minimumAge,
                languageTest,
                endedAt,
                startedAt,
                benefit,
                major,
                scholarshipId);
        return ResponseEntity.status(HttpStatus.OK).body("Scholarship has been updated successfully.");
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public List<Scholarship> getAllScholarship(){
        return scholarshipService.getAllScholarship();
    }

    @GetMapping("/get-all-by-month")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public List<ScholarshipResponseDTO> getAllScholarship(@RequestParam int month){
        return scholarshipService.getAllScholarshipByOpeningMonth(month);
    }

    @DeleteMapping("/delete")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> deleteScholarship(@RequestParam Long id){
        Scholarship scholarship = scholarshipService.getScholarshipById(id);
        scholarshipService.deleteScholarship(scholarship);
        return ResponseEntity.status(HttpStatus.OK).body("Scholarship Request has been deleted successfully.");
    }

    @GetMapping("/get-by-scholarship-id")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ScholarshipResponseDTO getScholarshipDetailsById(@RequestParam Long scholarshipId){
        return scholarshipService.getScholarshipDetailById(scholarshipId);
    }

    @GetMapping("/get-by-keyword")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public List<ScholarshipResponseDTO> getScholarshipsByKeyword(@RequestParam String keyword){
        return scholarshipService.getScholarshipsByKeyword(keyword);
    }

    @GetMapping("/get-by-filters")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<List<Scholarship>> getScholarshipsByFilters(
            @RequestParam(name = "degreeFilter", required = false) ScholarshipDegree degreeFilter,
            @RequestParam(name = "fundFilter", required = false) ScholarshipType fundFilter,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sortByOpeningDate", required = false, defaultValue = "asc") String sortByOpeningDate) {

        List<Scholarship> scholarships = scholarshipService.getScholarshipsByFilters(degreeFilter, fundFilter, sortByOpeningDate, keyword);
        return new ResponseEntity<>(scholarships, HttpStatus.OK);
    }
}
