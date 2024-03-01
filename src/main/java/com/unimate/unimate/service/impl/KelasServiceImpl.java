package com.unimate.unimate.service.impl;

import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.repository.KelasRepository;
import com.unimate.unimate.service.KelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KelasServiceImpl implements KelasService {
    @Autowired
    private KelasRepository kelasRepository;

    @Override
    public void saveKelas(Kelas kelas) {
        kelasRepository.save(kelas);
    }

    @Override
    public List<Kelas> getAllKelas() {
        return kelasRepository.findAll();
    }

    @Override
    public Kelas getKelasById(Long id) {
        return kelasRepository.findKelasById(id);
    }
}
