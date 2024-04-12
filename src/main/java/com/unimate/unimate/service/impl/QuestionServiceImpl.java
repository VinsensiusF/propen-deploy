package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.dto.UpdateQuestionContentDTO;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.exception.QuestionContentNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.repository.QuestionContentRepository;
import com.unimate.unimate.service.QuestionService;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionContentRepository questionContentRepository;
    private final UjianService ujianService;

    @Autowired
    public QuestionServiceImpl(QuestionContentRepository questionContentRepository, UjianService ujianService){
        this.questionContentRepository = questionContentRepository;
        this.ujianService = ujianService;
    }


    @Override
    public void saveQuesionContent(QuestionContent questionContent) {
        questionContentRepository.save(questionContent);
    }


    @Override
    public List<QuestionContent> getAllQuestionContent() {
        return questionContentRepository.findAll();
    };

    @Override
    public QuestionContent getQuestionContentById(Long id) {
        return questionContentRepository.findQuestionContentById(id);
    }

    @Override
    public List<QuestionContent> getQuestionContentsByUjian(Long ujianId) {
        Ujian ujian = ujianService.getUjianById(ujianId);
        if (ujian == null) {
            throw new UjianNotFoundException();
        }
        return ujian.getQuestionContents();
    }

    public QuestionContent createQuestionContent(QuestionContentDTO questionContentDTO) {
        Ujian ujian = ujianService.getUjianById(questionContentDTO.getUjianId());
        if (ujian == null) {
            throw new UjianNotFoundException();
        }

        QuestionContent questionContent = new QuestionContent();
        questionContent.setQuestionSentence(questionContentDTO.getQuestionSentence());
        questionContent.setA(questionContentDTO.getA());
        questionContent.setB(questionContentDTO.getB());
        questionContent.setC(questionContentDTO.getC());
        questionContent.setD(questionContentDTO.getD());
        questionContent.setCorrectAnswer(questionContentDTO.getCorrectAnswer());
        questionContent.setUjian(ujian);
        questionContentRepository.save(questionContent);

        return questionContent;
    }

    @Override
    public QuestionContent updateQuestionContent(UpdateQuestionContentDTO updateQuestionContentDTO) {
        QuestionContent questionContent = getQuestionContentById(updateQuestionContentDTO.getId());
        if (questionContent == null) {
            throw new QuestionContentNotFoundException();
        }

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(updateQuestionContentDTO.toString());
        System.out.println(updateQuestionContentDTO.getQuestionSentence());

        // QuestionContents aren't allowed to change Ujian
        questionContent.setA(updateQuestionContentDTO.getA());
        questionContent.setB(updateQuestionContentDTO.getB());
        questionContent.setC(updateQuestionContentDTO.getC());
        questionContent.setD(updateQuestionContentDTO.getD());
        questionContent.setQuestionSentence(updateQuestionContentDTO.getQuestionSentence());
        questionContentRepository.save(questionContent);

        return questionContent;
    }

    @Override
    public void deleteQuestionContent(QuestionContent questionContent) {
        questionContentRepository.delete(questionContent);
    }


}
