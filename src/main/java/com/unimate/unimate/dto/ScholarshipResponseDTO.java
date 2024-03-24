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
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Date startedAt;

    @NotNull
    private Date endedAt;

    //OPEN, CLOSED
    @NotNull
    private ScholarshipStatus scholarshipStatus;

    //FULLY_FUNDED, PARTIALLY_FUNDED, SELF_FUNDED
    @NotNull
    private ScholarshipType scholarshipType;

    //D3, D4, S1, S2, S3
    @NotNull
    private Set<ScholarshipDegree> scholarshipDegrees;

    @NotNull
    private String ipkMinimal;

    @NotNull
    private String university;

    @NotNull
    private long minimalAge;

    @NotNull
    private ArrayList<String> benefit;

    @NotNull
    private ArrayList<String> major;
}
