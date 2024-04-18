package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.UjianSiswaDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.entity.UjianSiswa;
import com.unimate.unimate.exception.AccountNotFoundException;
import com.unimate.unimate.exception.UjianNotFoundException;
import com.unimate.unimate.exception.UjianSiswaNotFoundException;
import com.unimate.unimate.repository.UjianSiswaRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.UjianService;
import com.unimate.unimate.service.UjianSiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UjianSiswaServiceImpl implements UjianSiswaService {

    private final AccountService accountService;
    private final UjianService ujianService;
    private final UjianSiswaRepository ujianSiswaRepository;

    @Autowired
    public UjianSiswaServiceImpl(UjianService ujianService, AccountService accountService, UjianSiswaRepository ujianSiswaRepository) {
        this.ujianService = ujianService;
        this.accountService = accountService;
        this.ujianSiswaRepository = ujianSiswaRepository;
    }

    @Override
    public void saveUjianSiswa(UjianSiswa ujianSiswa) {
        ujianSiswaRepository.save(ujianSiswa);
    }

    @Override
    public void deleteUjianSiswa(UjianSiswa ujianSiswa) {
        ujianSiswaRepository.delete(ujianSiswa);
    }

    @Override
    public UjianSiswa takeExam(UjianSiswaDTO ujianSiswaDTO) {
        Optional<Account> siswa = accountService.getAccountById(ujianSiswaDTO.getStudentId());
        if (siswa.isEmpty()) {
            throw new AccountNotFoundException();
        }

        Ujian ujian = ujianService.getUjianById(ujianSiswaDTO.getUjianId());
        if (ujian == null) {
            throw new UjianNotFoundException();
        }

        UjianSiswa ujianSiswa = new UjianSiswa();
        ujianSiswa.setSiswa(siswa.get());
        ujianSiswa.setUjian(ujian);
        ujianSiswaRepository.save(ujianSiswa);

        return ujianSiswa;
    }

    @Override
    public void abandonExam(UjianSiswaDTO ujianSiswaDTO) {
        UjianSiswa ujianSiswa = getUjianSiswaByUjianIdAndSiswaId(ujianSiswaDTO.getUjianId(), ujianSiswaDTO.getStudentId());

        if (ujianSiswa == null) {
            throw new UjianSiswaNotFoundException();
        }

        deleteUjianSiswa(ujianSiswa);
    }

    @Override
    public UjianSiswa getUjianSiswaById(Long id) {
        UjianSiswa ujianSiswa = ujianSiswaRepository.findUjianSiswaById(id);
        if (ujianSiswa == null) {
            throw new UjianSiswaNotFoundException();
        }
        return ujianSiswa;
    }

    @Override
    public List<UjianSiswa> getUjianSiswaByUjianId(Long ujianId) {
        Ujian ujian = ujianService.getUjianById(ujianId);
        if (ujian == null) {
            throw new UjianNotFoundException();
        }

        List<UjianSiswa> list = ujianSiswaRepository.findUjianSiswaListByUjianId(ujianId);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<UjianSiswa> getUjianSiswaBySiswaId(Long siswaId) {
        Optional<Account> siswa = accountService.getAccountById(siswaId);
        if (siswa.isEmpty()) {
            throw new AccountNotFoundException();
        }

        List<UjianSiswa> list = ujianSiswaRepository.findUjianSiswaListBySiswaId(siswaId);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public UjianSiswa getUjianSiswaByUjianIdAndSiswaId(Long ujianId, Long siswaId) {
        return ujianSiswaRepository.findUjianSiswaByUjianIdAndSiswaId(ujianId, siswaId);
    }

    @Override
    public UjianSiswa gradeExam(UjianSiswaDTO ujianSiswaDTO, Double grade) {
        UjianSiswa ujianSiswa = getUjianSiswaByUjianIdAndSiswaId(ujianSiswaDTO.getUjianId(), ujianSiswaDTO.getStudentId());

        if (ujianSiswa == null) {
            throw new UjianSiswaNotFoundException();
        }

        ujianSiswa.setGrade(grade);
        ujianSiswaRepository.save(ujianSiswa);
        return ujianSiswa;
    }
}
