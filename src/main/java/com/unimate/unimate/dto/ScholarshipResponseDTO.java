package com.unimate.unimate.dto;

import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipStatus;
import com.unimate.unimate.enums.ScholarshipType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ScholarshipResponseDTO {
    private Long scholarshipId;

    private String title;

    private String university;

    private String minimumAge;

    private String minimumGPA;

    private String description;

    private String languageTest;

    private String standardizedTest;

    private Date startedAt;

    private Date endedAt;

    //OPEN, CLOSED
    private ScholarshipStatus scholarshipStatus;

    //FULLY_FUNDED, PARTIALLY_FUNDED, SELF_FUNDED
    private ScholarshipType scholarshipType;

    //D3, D4, S1, S2, S3
    private Set<ScholarshipDegree> scholarshipDegrees;

    private ArrayList<String> benefit;

    private ArrayList<String> major;
}
