package com.unimate.unimate.dto;

import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipStatus;
import com.unimate.unimate.enums.ScholarshipType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ScholarshipDTO {
    @NotNull
    private String title;

    @NotNull
    private String university;

    @NotNull
    private String minimumAge;

    @NotNull
    private String minimumGPA;

    @NotNull
    private String description;

    @NotNull
    private String languageTest;

    @NotNull
    private String standardizedTest;

    @NotNull
    private Date startedAt;

    @NotNull
    private Date endedAt;

    //OPEN, CLOSED
//    @NotNull
//    private ScholarshipStatus scholarshipStatus;

    //FULLY_FUNDED, PARTIALLY_FUNDED, SELF_FUNDED
    @NotNull
    private ScholarshipType scholarshipType;

    //D3, D4, S1, S2, S3
    @NotNull
    private Set<ScholarshipDegree> scholarshipDegrees;

    @NotNull
    private ArrayList<String> benefit;

    @NotNull
    private ArrayList<String> major;
}
