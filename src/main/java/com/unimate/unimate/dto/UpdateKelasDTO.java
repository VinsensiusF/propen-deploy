package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

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

    private Long teacherId;

}
