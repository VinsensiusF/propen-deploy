package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KelasRatingDTO {
    @NotNull
    private Long studentId;
    @NotNull
    private Long kelasId;
    @NotNull
    private Integer rating;
}
