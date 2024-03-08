package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ScholarshipDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Date startedAt;

    @NotNull
    private Date endedAt;
}
