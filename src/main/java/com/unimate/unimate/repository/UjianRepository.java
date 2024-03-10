package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Ujian;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UjianRepository extends JpaRepository<Ujian, Long> {
    Ujian findUjianById(Long id);
}
