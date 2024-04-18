package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.UjianDTO;
import com.unimate.unimate.dto.UpdateUjianDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasGuru;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.exception.AccountNotFoundException;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.repository.UjianRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasGuruService;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UjianServiceImpl implements UjianService {
    private final UjianRepository ujianRepository;
    private final KelasService kelasService;
    private final AccountService accountService;
    private final KelasGuruService kelasGuruService;

    @Autowired
    public UjianServiceImpl(UjianRepository ujianRepository, KelasService kelasService, AccountService accountService, KelasGuruService kelasGuruService){
        this.ujianRepository = ujianRepository;
        this.kelasService = kelasService;
        this.accountService = accountService;
        this.kelasGuruService = kelasGuruService;
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
        Kelas kelas = kelasService.getKelasById(updateUjianDTO.getKelasId());
        ujian.setKelas(kelas);
        saveUjian(ujian);

        return ujian;
    }

    @Override
    public void deleteUjian(Ujian ujian) {
        ujianRepository.delete(ujian);
    }


    @Override
    public List<Ujian> findUjianListByGuruId(Long guruId) {
        Optional<Account> guru = accountService.getAccountById(guruId);
        if (guru.isEmpty()) {
            throw new AccountNotFoundException();
        }

        List<Ujian> ujianList = new ArrayList<>();

        List<Kelas> kelasList = kelasGuruService.findAllKelasTaughtByAGuru(guruId);
        for (Kelas kelas : kelasList) {
            ujianList.addAll(ujianRepository.findAllByKelas(kelas));
        }
        return ujianList;

    }
}
