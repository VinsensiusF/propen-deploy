package com.unimate.unimate.dto;

import com.unimate.unimate.entity.Ujian;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionContentDTO {

    @NotNull
    private Long ujianId;

    @NotBlank
    private String questionSentence;

    @NotBlank
    private String a;

    @NotBlank
    private String b;

    @NotBlank
    private String c;

    @NotBlank
    private String d;

    @NotBlank
    private String correctAnswer;
}