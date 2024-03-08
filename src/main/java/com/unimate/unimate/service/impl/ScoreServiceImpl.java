package com.unimate.unimate.service.impl;

import com.unimate.unimate.entity.Score;
import com.unimate.unimate.repository.ScoreRepository;
import com.unimate.unimate.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository){
        this.scoreRepository = scoreRepository;
    }

    @Override
    public void saveScore(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public List<Score> getAllScore() {
        return scoreRepository.findAll();
    }

    @Override
    public Score getScoreById(Long id) {
        return scoreRepository.findScoreById(id);
    }
}
