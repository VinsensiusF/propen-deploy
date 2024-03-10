package com.unimate.unimate.service;

import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;

import java.util.ArrayList;
import java.util.List;

public interface QuestionService {
    void saveQuestion(Question question);

    List<Question> getAllQuestion();

    Question getQuestionById(Long id);

    ArrayList<QuestionContent> getQuestionContentsByUjian(Ujian ujian);

    ArrayList<Question> getQuestionsByUjian(Ujian ujian);

    Question createQuestion(Ujian ujian);

    QuestionContent createQuestionContent(Question question, QuestionContentDTO questionContentDTO);
}
