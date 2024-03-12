package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UpdateKelasDTO {
    @NotNull
    private Long id;
    @NotNull
    private String name;

//    @NotNull
//    private Float rating;

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
    @NotNull
    private Boolean isFinished;

    private Long teacherId;

}
