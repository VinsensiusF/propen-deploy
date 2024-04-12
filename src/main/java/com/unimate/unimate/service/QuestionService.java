package com.unimate.unimate.service;

import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.dto.UpdateQuestionContentDTO;
import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;

import java.util.ArrayList;
import java.util.List;

public interface QuestionService {
//    void saveQuestion(Question question);

    void saveQuesionContent(QuestionContent questionContent);

//    List<Question> getAllQuestion();

    List<QuestionContent> getAllQuestionContent();

//    Question getQuestionById(Long id);

    QuestionContent getQuestionContentById(Long id);

    List<QuestionContent> getQuestionContentsByUjian(Long ujianId);

//    ArrayList<Question> getQuestionsByUjian(Ujian ujian);

//    Question createQuestion(Ujian ujian);

    QuestionContent createQuestionContent(QuestionContentDTO questionContentDTO);

    QuestionContent updateQuestionContent(UpdateQuestionContentDTO updateQuestionContentDTO);
    void deleteQuestionContent(QuestionContent questionContent);
}
