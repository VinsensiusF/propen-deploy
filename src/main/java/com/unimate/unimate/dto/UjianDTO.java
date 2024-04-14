package com.unimate.unimate.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UjianDTO {

    @NotNull
    private long kelasId;

    @NotNull
    private String title;

    // Duration in seconds
    @NotNull
    private Long duration;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;
}
