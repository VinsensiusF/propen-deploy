package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PartnershipDTO {
    @NotNull
    private String company;

    @NotNull
    private Long user;

    @NotNull
    private String companyEmail;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String description;
}
