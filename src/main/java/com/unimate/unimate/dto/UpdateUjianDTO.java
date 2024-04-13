package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUjianDTO {
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Long duration;
}
