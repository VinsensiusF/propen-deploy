package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateCourseContentDTO {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer type;

    @NotNull
    private String description;

    @NotNull
    private String link;

    @NotNull
    private Long courseId;
}
