package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.CreateKelasDTO;
import com.unimate.unimate.dto.UpdateKelasDTO;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.repository.KelasRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KelasServiceImpl implements KelasService {
    private final KelasRepository kelasRepository;
    private final AccountService accountService;

    @Autowired
    public KelasServiceImpl(KelasRepository kelasRepository, AccountService accountService){
        this.kelasRepository = kelasRepository;
        this.accountService = accountService;
    }

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

    @Override
    public Kelas createKelas(CreateKelasDTO createKelasDTO) {
        Kelas kelas = new Kelas();
        kelas.setTeacher(accountService.getAccountById(createKelasDTO.getTeacherId()).get());
        kelas.setName(createKelasDTO.getName());
        saveKelas(kelas);
        return kelas;
    }

    // TODO implement update siswa in kelas
    // TODO implement many to many siswa in kelas
    @Override
    public Kelas updateKelas(UpdateKelasDTO updateKelasDTO) {
        Kelas kelas = getKelasById(updateKelasDTO.getId());
        kelas.setName(updateKelasDTO.getName());
        kelas.setTeacher(accountService.getAccountById(updateKelasDTO.getTeacherId()).get());
        saveKelas(kelas);
        return kelas;
    }
}
