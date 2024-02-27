package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findScoreById(Long id);
}
