package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionContentDTO {

    @NotNull
    private Long ujianId;
    @NotNull
    private String questionSentence;

    private String questionText;

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