package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UjianResultDTO {
    @NotNull
    private Long ujianId;

    @NotNull
    private Long siswaId;

    @NotNull
    private List<StudentAnswerDTO> list;
}
