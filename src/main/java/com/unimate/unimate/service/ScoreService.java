package com.unimate.unimate.service;

import com.unimate.unimate.entity.Score;

import java.util.List;

public interface ScoreService {
    void saveScore(Score score);

    List<Score> getAllScore();

    Score getScoreById(Long id);
}
