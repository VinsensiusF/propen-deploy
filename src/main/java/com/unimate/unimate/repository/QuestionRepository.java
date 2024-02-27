package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionById(Long id);
}
