package com.unimate.unimate.service;

import com.unimate.unimate.entity.Ujian;

import java.util.List;

public interface UjianService {
    void saveUjian(Ujian ujian);

    List<Ujian> getAllUjian();

    Ujian getUjianById(Long id);
}
