package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KelasGuruDTO {
    @NotNull
    private Long kelasId;

    @NotNull
    private Long guruId;
}
