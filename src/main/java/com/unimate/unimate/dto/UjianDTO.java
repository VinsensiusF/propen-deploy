package com.unimate.unimate.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UjianDTO {

    @NotNull
    private long kelasId;

    @Future
    @NotNull
    private Date startedAt;

    // Duration in seconds
    @NotNull
    private Long duration;
}
