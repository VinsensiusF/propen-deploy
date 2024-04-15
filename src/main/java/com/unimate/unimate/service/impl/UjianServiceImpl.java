package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.UjianDTO;
import com.unimate.unimate.dto.UpdateUjianDTO;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.repository.UjianRepository;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UjianServiceImpl implements UjianService {
    private final UjianRepository ujianRepository;
    private final KelasService kelasService;

    @Autowired
    public UjianServiceImpl(UjianRepository ujianRepository, KelasService kelasService){
        this.ujianRepository = ujianRepository;
        this.kelasService = kelasService;
    }

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

    @Override
    public Ujian createUjian(UjianDTO ujianDTO) {
        Kelas kelas = kelasService.getKelasById(ujianDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(ujianDTO.getStartDate());
        System.out.println(ujianDTO.getStartDate().getClass().getName());
        System.out.println("");
        System.out.println(ujianDTO.getEndDate());
        System.out.println(ujianDTO.getEndDate().getClass().getName());
        Ujian ujian = new Ujian();
        ujian.setKelas(kelasService.getKelasById(ujianDTO.getKelasId()));
        ujian.setTitle(ujianDTO.getTitle());
        ujian.setDuration(ujianDTO.getDuration());
        ujian.setStartDate(ujianDTO.getStartDate());
        ujian.setEndDate(ujianDTO.getEndDate());
        saveUjian(ujian);

        return ujian;
    }

    @Override
    public Ujian updateUjian(UpdateUjianDTO updateUjianDTO) {
        Ujian ujian = getUjianById(updateUjianDTO.getId());
        if (ujian == null) {
            throw new UjianNotFoundException();
        }
        ujian.setDuration(updateUjianDTO.getDuration());
        ujian.setTitle(updateUjianDTO.getTitle());
        ujian.setEndDate(updateUjianDTO.getEndDate());
        ujian.setStartDate(updateUjianDTO.getStartDate());
        saveUjian(ujian);

        return ujian;
    }

    @Override
    public void deleteUjian(Ujian ujian) {
        ujianRepository.delete(ujian);
    }



}
