package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PartnershipDTO {
    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String description;
}
