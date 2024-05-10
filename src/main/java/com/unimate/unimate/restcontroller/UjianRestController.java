package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.*;
import com.unimate.unimate.entity.*;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.AccountNotFoundException;
import com.unimate.unimate.exception.QuestionContentNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.exception.UjianSiswaNotFoundException;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.QuestionService;
import com.unimate.unimate.service.UjianService;
import com.unimate.unimate.service.UjianSiswaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ujian")
public class UjianRestController {
    private final UjianService ujianService;
    private final UjianSiswaService ujianSiswaService;
    private final QuestionService questionService;
    private final AccountService accountService;

    @Autowired
    public UjianRestController(UjianService ujianService, UjianSiswaService ujianSiswaService, QuestionService questionService, AccountService accountService){
        this.ujianService = ujianService;
        this.ujianSiswaService = ujianSiswaService;
        this.questionService = questionService;
        this.accountService = accountService;
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<List<Ujian>> findAllUjian() {
        return ResponseEntity.ok(ujianService.getAllUjian());
    }

    @GetMapping("/get-all/by-teacher/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> findAllUjianByTeacher(@PathVariable("id") Long guruId) {
        List<Ujian> ujianList = ujianService.findUjianListByGuruId(guruId);
        return ResponseEntity.ok(ujianList);
    }

    @GetMapping("/get-all/by-kelas/{id}")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER, RoleEnum.STUDENT})
    public ResponseEntity<?> findAllUjianByKelas(@PathVariable("id") Long kelasId) {
        List<Ujian> ujianList = ujianService.findUjianListByKelasId(kelasId);
        return ResponseEntity.ok(ujianList);
    }

    @GetMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.STUDENT, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> findUjianById(@PathVariable("id") Long id) {
        Ujian ujian = ujianService.getUjianById(id);
        if (ujian == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ujian is not found.");
        }

        return ResponseEntity.ok(ujian);
    }

    @PostMapping("/create")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> createUjian(@Valid @RequestBody UjianDTO ujianDTO){
        Ujian ujian = ujianService.createUjian(ujianDTO);
        if (ujian != null) {
            return ResponseEntity.ok(ujian);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Ujian.");
        }
    }

    @PutMapping("/update")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> updateUjian(@Valid @RequestBody UpdateUjianDTO updateUjianDTO) {
        Ujian ujian = ujianService.updateUjian(updateUjianDTO);
        return ResponseEntity.ok(ujian);
    }

    @DeleteMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER})
    public ResponseEntity<?> deleteUjian(@PathVariable("id") Long id) {
        Ujian ujian = ujianService.getUjianById(id);
        if (ujian == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ujian is not found.");
        }
        ujianService.deleteUjian(ujian);
        return ResponseEntity.ok("Ujian has been successfully deleted.");
    }

    // UJIANSISWA
    @PostMapping("/take-exam")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN})
    public ResponseEntity<?> takeExam(@Valid @RequestBody UjianSiswaDTO ujianSiswaDTO) {
        UjianSiswa ujianSiswa = ujianSiswaService.takeExam(ujianSiswaDTO);
        return ResponseEntity.ok(ujianSiswa);
    }

    @PostMapping("/abandon-exam")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> abandonExam(@Valid @RequestBody UjianSiswaDTO ujianSiswaDTO) {
        ujianSiswaService.abandonExam(ujianSiswaDTO);
        return ResponseEntity.ok("Siswa has been successfully abandoned Ujian");
    }

    @GetMapping("/ujian-siswa/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.STUDENT})
    public ResponseEntity<?> getUjianSiswaById(@PathVariable("id") Long id) {
        UjianSiswa ujianSiswa = ujianSiswaService.getUjianSiswaById(id);
        return ResponseEntity.ok(ujianSiswa);
    }

    @GetMapping("/ujian-siswa/list-by-ujian/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> getUjianSiswaByUjianId(@PathVariable("id") Long ujianId) {
        List<UjianSiswa> ujianSiswaList = ujianSiswaService.getUjianSiswaByUjianId(ujianId);
        return ResponseEntity.ok(ujianSiswaList);
    }

    @GetMapping("/ujian-siswa/list-by-ujian-graded/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> getUjianSiswaByUjianIdGraded(@PathVariable("id") Long ujianId) {
        List<UjianSiswaResponseDTO> ujianSiswaList = ujianSiswaService.getUjianSiswaByUjianIdGraded(ujianId);
        return ResponseEntity.ok(ujianSiswaList);
    }

    @GetMapping("/ujian-siswa/list-by-ujian-ungraded/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> getUjianSiswaByUjianIdUngraded(@PathVariable("id") Long ujianId) {
        List<UjianSiswaResponseDTO> ujianSiswaList = ujianSiswaService.getUjianSiswaByUjianIdUngraded(ujianId);
        return ResponseEntity.ok(ujianSiswaList);
    }


    @GetMapping("/ujian-siswa/list-by-siswa/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> getUjianSiswaBySiswaId(@PathVariable("id") Long siswaId) {
        List<UjianSiswa> ujianSiswaList = ujianSiswaService.getUjianSiswaBySiswaId(siswaId);
        return ResponseEntity.ok(ujianSiswaList);
    }

    @GetMapping("/ujian-siswa/by-ujian-and-siswa/{ujianId}/{siswaId}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.STUDENT})
    public ResponseEntity<?> getUjianSiswaByUjianIdAndSiswaId(@PathVariable("ujianId") Long ujianId, @PathVariable("siswaId") Long siswaId) {
        UjianSiswa ujianSiswaList = ujianSiswaService.getUjianSiswaByUjianIdAndSiswaId(ujianId, siswaId);
        return ResponseEntity.ok(ujianSiswaList);
    }

    @PostMapping("/grade")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER,RoleEnum.STUDENT})
    public ResponseEntity<?> gradeUjian(@RequestBody UjianResultDTO ujianResultDTO) {

        Ujian ujian = ujianService.getUjianById(ujianResultDTO.getUjianId());
        if (ujian == null) {
            throw new UjianNotFoundException();
        }

        Optional<Account> siswa = accountService.getAccountById(ujianResultDTO.getSiswaId());
        if(siswa.isEmpty()) {
            throw new AccountNotFoundException();
        }

        UjianSiswa ujianSiswa = ujianSiswaService.getUjianSiswaByUjianIdAndSiswaId(ujianResultDTO.getUjianId(), ujianResultDTO.getSiswaId());
        if (ujianSiswa == null) {
            throw new UjianSiswaNotFoundException();
        }

        int questionsCorrect = 0;

        for (StudentAnswerDTO answerDTO : ujianResultDTO.getList()) {
            QuestionContent questionContent = questionService.getQuestionContentById(answerDTO.getId());

            if (questionContent == null) {
                throw new QuestionContentNotFoundException();
            }

            if (answerDTO.getOption().equals(questionContent.getCorrectAnswer())) {
                questionsCorrect = questionsCorrect + 1;
            }
        }

        Double grade = (double) questionsCorrect / ujian.getQuestionContents().size() * 100;
        ujianSiswa.setGrade(Math.round(grade * 100.0) / 100.0);
        ujianSiswaService.saveUjianSiswa(ujianSiswa);
//        System.out.println("Soal yang dijawab: " + ujianResultDTO.getList().size());
//        System.out.println("Soal benar: " + questionsCorrect);
//        System.out.println("Total soal yang ada: " + ujian.getQuestionContents().size());
        return ResponseEntity.ok(ujianSiswa);

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
    }


}
