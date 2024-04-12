package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateQuestionContentDTO {

    @NotNull
    private Long id;

    @NotNull
    private String questionSentence;

    @NotNull
    private String a;

    @NotNull
    private String b;

    @NotNull
    private String c;

    @NotNull
    private String d;

    @NotNull
    private String correctAnswer;
}
