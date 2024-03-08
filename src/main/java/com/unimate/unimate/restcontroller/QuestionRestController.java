package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.QuestionService;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/question")
public class QuestionRestController {
    private final QuestionService questionService;
    private final UjianService ujianService;

    @Autowired
    public QuestionRestController(QuestionService questionService, UjianService ujianService){
        this.questionService = questionService;
        this.ujianService = ujianService;
    }

    @PostMapping("/create")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<String> createQuestion(@RequestBody QuestionContentDTO questionContentDTO){
        Ujian ujian = ujianService.getUjianById(questionContentDTO.getUjianId());
        Question question = questionService.createQuestion(ujian);
        QuestionContent questionContent = questionService.createQuestionContent(question, questionContentDTO);

        if (question != null & questionContent != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Question has been created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Question.");
        }
    }

    @GetMapping("/get-by-ujian")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ArrayList<QuestionContent> getQuestionsByUjianId(@RequestParam Long ujianId){
        Ujian ujian = ujianService.getUjianById(ujianId);
        ArrayList<QuestionContent> questionContents = questionService.getQuestionContentsByUjian(ujian);

        if (questionContents != null) {
            return questionContents;
        } else {
            throw new RuntimeException("Failed to get the Questions");
        }
    }
}
