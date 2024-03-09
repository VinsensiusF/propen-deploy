package com.unimate.unimate.service;

import com.unimate.unimate.dto.CreateKelasDTO;
import com.unimate.unimate.dto.UpdateKelasDTO;
import com.unimate.unimate.entity.Kelas;

import java.util.List;

public interface KelasService {
    void saveKelas(Kelas kelas);

    List<Kelas> getAllKelas();

    Kelas getKelasById(Long id);

    Kelas createKelas(CreateKelasDTO createKelasDTO);

    Kelas updateKelas(UpdateKelasDTO updateKelasDTO);
}
