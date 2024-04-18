package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
public class UpdateUjianDTO {
    @NotNull
    private Long id;

    @NotNull
    private Long kelasId;

    @NotNull
    private String title;

    @NotNull
    private Long duration;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;
}
