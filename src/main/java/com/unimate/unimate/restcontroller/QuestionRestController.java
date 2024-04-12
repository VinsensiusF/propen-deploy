package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.QuestionContentDTO;
import com.unimate.unimate.dto.StudentAnswerDTO;
import com.unimate.unimate.dto.UjianResultDTO;
import com.unimate.unimate.dto.UpdateQuestionContentDTO;
import com.unimate.unimate.entity.QuestionContent;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.QuestionContentNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.service.QuestionService;
import com.unimate.unimate.service.UjianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createQuestion(@RequestBody QuestionContentDTO questionContentDTO){
        QuestionContent questionContent = questionService.createQuestionContent(questionContentDTO);

        if (questionContent != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionContent);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Question.");
        }
    }

    @GetMapping("/get-by-ujian/{id}")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public List<QuestionContent> getQuestionsByUjianId(@PathVariable("id") Long ujianId){
        Ujian ujian = ujianService.getUjianById(ujianId);

        if (ujian == null) {
            throw new UjianNotFoundException();
        }
        List<QuestionContent> questionContents = questionService.getQuestionContentsByUjian(ujianId);

        if (questionContents != null) {
            return questionContents;
        } else {
            throw new RuntimeException("Failed to get the Questions");
        }
    }

    @GetMapping("/{id}")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<QuestionContent> getQuestionById(@PathVariable("id") Long id) {
        QuestionContent questionContent = questionService.getQuestionContentById(id);
        if (questionContent == null) {
            throw new QuestionContentNotFoundException();
        }
        return ResponseEntity.ok(questionContent);
    }

    @PutMapping("/update")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER})
    public ResponseEntity<QuestionContent> updateQuestionContent(@Valid @RequestBody UpdateQuestionContentDTO updateQuestionContentDTO) {
        QuestionContent questionContent = questionService.updateQuestionContent(updateQuestionContentDTO);
        return ResponseEntity.ok(questionContent);
    }

    @DeleteMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER})
    public ResponseEntity<?> deleteQuestionContent(@PathVariable("id") Long id) {
        QuestionContent questionContent = questionService.getQuestionContentById(id);

        if (questionContent == null) {
            throw new QuestionContentNotFoundException();
        }
        questionService.deleteQuestionContent(questionContent);
        return ResponseEntity.ok("QuestionContent has been successfully deleted.");
    }

    @PostMapping("/testgrade")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER,RoleEnum.STUDENT})
    public ResponseEntity<?> gradeUjian(@RequestBody UjianResultDTO ujianResultDTO) {
        Integer grade = 0;

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("UjianID: "+ ujianResultDTO.getUjianId());

        for (StudentAnswerDTO answerDTO :
                ujianResultDTO.getList()) {
            System.out.println("QuestionID: " + answerDTO.getId());
            System.out.println("Answer: " + answerDTO.getOption());
            System.out.println("");
        }

//        for (StudentAnswerDTO answerDTO : studentAnswerDTOList) {
//            QuestionContent questionContent = questionService.getQuestionContentById(answerDTO.getId());
//            if (questionContent == null) {
//                throw new QuestionContentNotFoundException();
//            }
//
//            boolean result = answerDTO.getOption().equals(questionContent.getCorrectAnswer());
//            if (result) {
//                grade++;
//            }
//        }

        return ResponseEntity.ok("ok");
    }
}
