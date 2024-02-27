package com.unimate.unimate.service;

import com.unimate.unimate.entity.Kelas;

import java.util.List;

public interface KelasService {
    void saveKelas(Kelas kelas);

    List<Kelas> getAllKelas();

    Kelas getKelasById(Long id);
}
