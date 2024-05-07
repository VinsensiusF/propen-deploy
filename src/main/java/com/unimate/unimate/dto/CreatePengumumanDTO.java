package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreatePengumumanDTO {
    @NotNull
    private Long guruId;
    @NotNull
    private Long kelasId;
    @NotNull
    private String headerContent;
    @NotNull
    private String content;
}
