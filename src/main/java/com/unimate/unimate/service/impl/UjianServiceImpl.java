package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.UjianDTO;
import com.unimate.unimate.entity.Question;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.repository.QuestionContentRepository;
import com.unimate.unimate.repository.QuestionRepository;
import com.unimate.unimate.repository.UjianRepository;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.QuestionService;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UjianServiceImpl implements UjianService {
    private final UjianRepository ujianRepository;
    private final QuestionService questionService;
    private final QuestionContentRepository questionContentRepository;
    private final KelasService kelasService;

    @Autowired
    public UjianServiceImpl(UjianRepository ujianRepository, KelasService kelasService, QuestionService questionService, QuestionContentRepository questionContentRepository){
        this.ujianRepository = ujianRepository;
        this.kelasService = kelasService;
        this.questionService = questionService;
        this.questionContentRepository = questionContentRepository;
    }

    @Override
    public void saveUjian(Ujian ujian) {
        ujianRepository.save(ujian);
    }

    @Override
    public List<Ujian> getAllUjian() {
        return ujianRepository.findAll();
    }

    @Override
    public Ujian getUjianById(Long id) {
        return ujianRepository.findUjianById(id);
    }

    @Override
    public Ujian createUjian(UjianDTO ujianDTO) {
        Ujian ujian = new Ujian();
        ujian.setKelas(kelasService.getKelasById(ujianDTO.getKelasId()));
        ujian.setStartedAt(ujianDTO.getStartedAt());
        ujian.setDuration(ujianDTO.getDuration());
        saveUjian(ujian);

        return ujian;
    }

    @Override
    public void deleteUjian(Ujian ujian) {
        ArrayList<Question> questions = questionService.getQuestionsByUjian(ujian);
    }
}
