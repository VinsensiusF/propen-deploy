package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudentAnswerDTO {
    @NotNull
    private Long id;

    @NotNull
    private String option;
}
