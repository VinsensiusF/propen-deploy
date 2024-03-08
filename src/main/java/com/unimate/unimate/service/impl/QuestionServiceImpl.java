package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.repository.QuestionContentRepository;
import com.unimate.unimate.repository.QuestionRepository;
import com.unimate.unimate.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionContentRepository questionContentRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionContentRepository questionContentRepository){
        this.questionRepository = questionRepository;
        this.questionContentRepository = questionContentRepository;
    }

    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public ArrayList<QuestionContent> getQuestionContentsByUjian(Ujian ujian) {
        ArrayList<Question> questions = getQuestionsByUjian(ujian);
        ArrayList<QuestionContent> contents = new ArrayList<>();
        for(Question question : questions){
            QuestionContent content = questionContentRepository.findQuestionContentsByQuestion(question);
            contents.add(content);
        }
        return contents;
    }

    @Override
    public ArrayList<Question> getQuestionsByUjian(Ujian ujian) {
        return questionRepository.findQuestionsByUjian(ujian);
    }

    @Override
    public Question createQuestion(Ujian ujian) {
        Question question = new Question();
        question.setUjian(ujian);
        questionRepository.save(question);

        return question;
    }

    public QuestionContent createQuestionContent(Question question, QuestionContentDTO questionContentDTO) {
        QuestionContent questionContent = new QuestionContent();
        questionContent.setQuestionSentence(questionContentDTO.getQuestionSentence());
        questionContent.setA(questionContentDTO.getA());
        questionContent.setB(questionContentDTO.getB());
        questionContent.setC(questionContentDTO.getC());
        questionContent.setD(questionContentDTO.getD());
        questionContent.setCorrectAnswer(questionContentDTO.getCorrectAnswer());
        questionContent.setQuestion(question);
        questionContentRepository.save(questionContent);

        return questionContent;
    }
}
