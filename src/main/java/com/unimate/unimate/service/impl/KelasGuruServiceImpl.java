package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.KelasGuruDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasGuru;
import com.unimate.unimate.exception.AccountNotFoundException;
import com.unimate.unimate.exception.KelasGuruNotFoundException;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.repository.KelasGuruRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasGuruService;
import com.unimate.unimate.service.KelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KelasGuruServiceImpl implements KelasGuruService {

    private final KelasService kelasService;
    private final AccountService accountService;
    private final KelasGuruRepository kelasGuruRepository;

    @Autowired
    public KelasGuruServiceImpl(KelasService kelasService, AccountService accountService, KelasGuruRepository kelasGuruRepository) {
        this.kelasService = kelasService;
        this.accountService = accountService;
        this.kelasGuruRepository = kelasGuruRepository;
    }

    @Override
    public void createKelasGuru(KelasGuru kelasGuru) {
        kelasGuruRepository.save(kelasGuru);
    }

    @Override
    public void deleteKelasGuru(KelasGuru kelasGuru) {
        kelasGuruRepository.delete(kelasGuru);
    }

    @Override
    public KelasGuru assignGuru(KelasGuruDTO kelasGuruDTO) {
        Kelas kelas = kelasService.getKelasById(kelasGuruDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        Optional<Account> guru = accountService.getAccountById(kelasGuruDTO.getGuruId());
        if (guru.isEmpty()) {
            throw new AccountNotFoundException();
        }

        KelasGuru kelasGuru = new KelasGuru();
        kelasGuru.setKelas(kelas);
        kelasGuru.setGuru(guru.get());
        kelasGuruRepository.save(kelasGuru);
        return kelasGuru;
    }

    @Override
    public void unassignGuru(KelasGuruDTO kelasGuruDTO) {
        KelasGuru kelasGuru = findKelasGuruByKelasIdAndGuruId(kelasGuruDTO.getKelasId(), kelasGuruDTO.getGuruId());
        if (kelasGuru == null) {
            throw new KelasGuruNotFoundException();
        }

        deleteKelasGuru(kelasGuru);
    }

    @Override
    public List<KelasGuru> findKelasGuruListByKelasId(Long kelasId) {
        List<KelasGuru> list = kelasGuruRepository.findKelasGuruListByKelasId(kelasId);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public KelasGuru findKelasGuruByKelasIdAndGuruId(Long kelasId, Long guruId) {
        return kelasGuruRepository.findKelasGuruByKelasIdAndGuruId(kelasId, guruId);
    }

    @Override
    public List<Account> findAllGuruInAClass(Long kelasId) {
        Kelas kelas = kelasService.getKelasById(kelasId);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        List<Account> guruList = new ArrayList<>();
        for (KelasGuru kg : kelas.getKelasGuru()) {
            guruList.add(kg.getGuru());
        }
        return guruList;
    }

    @Override
    public List<Kelas> findAllKelasTaughtByAGuru(Long guruId) {
        Optional<Account> guru = accountService.getAccountById(guruId);
        if (guru.isEmpty()) {
            throw new AccountNotFoundException();
        }

        List<Kelas> kelasList = new ArrayList<>();
        for (KelasGuru kg : guru.get().getKelasGuru()) {
            kelasList.add(kg.getKelas());
        }
        return kelasList;
    }
}
