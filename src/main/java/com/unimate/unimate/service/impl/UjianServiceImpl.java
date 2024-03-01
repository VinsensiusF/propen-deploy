package com.unimate.unimate.service.impl;

import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.repository.UjianRepository;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UjianServiceImpl implements UjianService {
    @Autowired
    private UjianRepository ujianRepository;

    @Override
    public void saveUjian(Ujian ujian) {
        ujianRepository.save(ujian);
    }

    @Override
    public List<Ujian> getAllUjian() {
        return ujianRepository.findAll();
    }

    @Override
    public Ujian getUjianById(Long id) {
        return ujianRepository.findUjianById(id);
    }
}
