package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.CreateKelasDTO;
import com.unimate.unimate.dto.SetTeacherDTO;
import com.unimate.unimate.dto.UpdateKelasDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.exception.AccountNotFoundException;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.repository.KelasRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        kelas.setName(createKelasDTO.getName());
        kelas.setRating(0f);
        kelas.setCategory(createKelasDTO.getCategory());
        saveKelas(kelas);
        return kelas;
    }

    // TODO implement update siswa in kelas
    // TODO implement many to many siswa in kelas
    @Override
    public Kelas updateKelas(UpdateKelasDTO updateKelasDTO) {
        Kelas kelas = getKelasById(updateKelasDTO.getId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        Optional<Account> teacher = accountService.getAccountById(updateKelasDTO.getTeacherId());
        if (teacher.isPresent()) {
            kelas.setTeacher(teacher.get());
        }



        kelas.setName(updateKelasDTO.getName());

//        kelas.setRating(updateKelasDTO.getRating());
        kelas.setCategory(updateKelasDTO.getCategory());
        saveKelas(kelas);
        return kelas;
    }

    @Override
    public Kelas setTeacherToKelas(SetTeacherDTO teacherDTO) {
        Optional<Account> teacher = accountService.getAccountById(teacherDTO.getTeacherId());
        if (teacher.isEmpty()) {
            throw new AccountNotFoundException();
        }
        Kelas kelas = getKelasById(teacherDTO.getKelasId());
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        kelas.setTeacher(teacher.get());
        kelasRepository.save(kelas);
        return kelas;
    }

    @Override
    public void deleteKelas(Kelas kelas) {
        kelasRepository.delete(kelas);
    }

    @Override
    public Kelas updateRating(Long kelasId, Float newRating) {
        Kelas kelas = kelasRepository.findKelasById(kelasId);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        kelas.setRating(newRating);
        kelasRepository.save(kelas);
        return kelas;
    }


}
