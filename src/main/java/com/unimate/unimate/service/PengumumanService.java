package com.unimate.unimate.service;

import java.util.List;

import com.unimate.unimate.dto.CreatePengumumanDTO;
import com.unimate.unimate.dto.UpdatePengumumanDTO;
import com.unimate.unimate.entity.Pengumuman;

public interface PengumumanService {

    void savePengumuman(Pengumuman pengumuman);

    Pengumuman updatePengumuman(UpdatePengumumanDTO pengumuman);

    void deletePengumuman(Pengumuman pengumuman);

    List<Pengumuman> getPengumumanByKelasId(Long id);
    
    Pengumuman getPengumumanById(Long id);

    Pengumuman createPengumuman(CreatePengumumanDTO pengumuman);
}
