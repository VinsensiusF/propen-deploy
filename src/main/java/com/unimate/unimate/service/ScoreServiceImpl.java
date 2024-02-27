package com.unimate.unimate.service;

import com.unimate.unimate.entity.Score;
import com.unimate.unimate.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService{
    @Autowired
    private ScoreRepository scoreRepository;

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
