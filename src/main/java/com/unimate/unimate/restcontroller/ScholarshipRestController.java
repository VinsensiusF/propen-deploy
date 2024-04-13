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
import com.unimate.unimate.service.ScholarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> createScholarship(@RequestBody ScholarshipDTO scholarshipDTO){
        Scholarship scholarship = scholarshipService.createScholarship(scholarshipDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Scholarship has been created successfully.");
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> updateScholarship(@RequestParam long scholarshipId, @RequestBody ScholarshipDTO scholarshipDTO){
        Scholarship scholarship = scholarshipService.updateScholarship(scholarshipDTO, scholarshipId);
        return ResponseEntity.status(HttpStatus.OK).body("Scholarship has been updated successfully.");
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public List<Scholarship> getAllScholarship(){
        return scholarshipService.getAllScholarship();
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
}
