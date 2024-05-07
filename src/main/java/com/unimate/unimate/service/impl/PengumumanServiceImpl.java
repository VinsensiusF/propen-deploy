package com.unimate.unimate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimate.unimate.dto.CreatePengumumanDTO;
import com.unimate.unimate.dto.UpdatePengumumanDTO;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Pengumuman;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.repository.PengumumanRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.PengumumanService;

@Service
public class PengumumanServiceImpl implements PengumumanService {

    private final PengumumanRepository pengumumanRepository;
    private final KelasService kelasService;
    private final AccountService accountService;

    @Autowired
    public PengumumanServiceImpl(PengumumanRepository pengumumanRepository, KelasService kelasService, AccountService accountService) {
        this.pengumumanRepository = pengumumanRepository;
        this.kelasService = kelasService;
        this.accountService = accountService;
    }

    @Override
    public void savePengumuman(Pengumuman pengumuman) {
        pengumumanRepository.save(pengumuman);
    }

    @Override
    public Pengumuman updatePengumuman(UpdatePengumumanDTO pengumumanDTO) {
        Pengumuman pengumuman = getPengumumanById(pengumumanDTO.getId());

        pengumuman.setHeaderContent(pengumumanDTO.getHeaderContent());

        pengumuman.setContent(pengumumanDTO.getContent());

        savePengumuman(pengumuman);

        return pengumuman;
    }

    @Override
    public void deletePengumuman(Pengumuman pengumuman) {
        pengumumanRepository.delete(pengumuman);
    }

    @Override
    public List<Pengumuman> getPengumumanByKelasId(Long id) {
        Kelas kelas = kelasService.getKelasById(id);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        return pengumumanRepository.findPengumumanByKelasId(id);

    }

    @Override
    public Pengumuman getPengumumanById(Long id) {
        return pengumumanRepository.findPengumumanById(id);
    }

    @Override
    public Pengumuman createPengumuman(CreatePengumumanDTO createPengumumanDTO) {
        Kelas kelas = kelasService.getKelasById(createPengumumanDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        Pengumuman pengumuman = new Pengumuman();
        pengumuman.setHeaderContent(createPengumumanDTO.getHeaderContent());
        pengumuman.setContent(createPengumumanDTO.getContent());
        pengumuman.setKelas(kelas);
        pengumuman.setAccount(accountService.getAccountById(createPengumumanDTO.getGuruId()).get());
        savePengumuman(pengumuman);

        return pengumuman;
    }
    
}
