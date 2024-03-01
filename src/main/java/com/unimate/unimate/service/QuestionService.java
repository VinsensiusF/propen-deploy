package com.unimate.unimate.service;

import com.unimate.unimate.entity.Question;

import java.util.List;

public interface QuestionService {
    void saveQuestion(Question question);

    List<Question> getAllQuestion();

    Question getQuestionById(Long id);
}
