package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.Ujian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionById(Long id);

    ArrayList<Question> findQuestionsByUjian(Ujian ujian);
}
