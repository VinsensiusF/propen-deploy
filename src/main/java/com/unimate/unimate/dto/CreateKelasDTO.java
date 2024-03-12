package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateKelasDTO {
    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private String description;
    @NotNull
    private Long price;
    @NotNull
    private List<String> benefits;
    @NotNull
    private List<String> syllabuses;
    private Long teacherId;

}
