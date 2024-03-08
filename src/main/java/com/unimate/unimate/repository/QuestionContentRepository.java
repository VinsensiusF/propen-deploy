package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.QuestionContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface QuestionContentRepository extends JpaRepository<QuestionContent, Long> {
    QuestionContent findQuestionContentById(Long id);
    QuestionContent findQuestionContentsByQuestion(Question question);
}
