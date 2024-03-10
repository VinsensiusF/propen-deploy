package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.KelasRatingDTO;
import com.unimate.unimate.dto.KelasSiswaDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasSiswa;
import com.unimate.unimate.exception.EntityNotFoundException;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.exception.KelasSiswaNotFoundException;
import com.unimate.unimate.repository.KelasSiswaRepository;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.KelasSiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KelasSiswaServiceImpl implements KelasSiswaService {

    private final KelasService kelasService;
    private final AccountService accountService;
    private final KelasSiswaRepository kelasSiswaRepository;

    @Autowired
    public KelasSiswaServiceImpl(KelasService kelasService, AccountService accountService, KelasSiswaRepository kelasSiswaRepository) {
        this.kelasService = kelasService;
        this.accountService = accountService;
        this.kelasSiswaRepository = kelasSiswaRepository;
    }

    @Override
    public KelasSiswa enrollStudent(KelasSiswaDTO kelasSiswaDTO) {
        Optional<Account> siswa = accountService.getAccountById(kelasSiswaDTO.getStudentId());
        Kelas kelas = kelasService.getKelasById(kelasSiswaDTO.getKelasId());

        if (siswa.isEmpty()) {
            throw new EntityNotFoundException();
        }

        if (kelas == null) {
            throw new KelasNotFoundException();
        }

        KelasSiswa kelasSiswa = new KelasSiswa();
        kelasSiswa.setKelas(kelas);
        kelasSiswa.setSiswa(siswa.get());
        kelasSiswaRepository.save(kelasSiswa);
        return kelasSiswa;
    }

    @Override
    public void disenrollStudent(KelasSiswaDTO kelasSiswaDTO) {

        KelasSiswa kelasSiswa = kelasSiswaRepository.findKelasSiswaByKelasIdAndSiswaId(kelasSiswaDTO.getKelasId(), kelasSiswaDTO.getStudentId());
        if (kelasSiswa == null) {
            throw new KelasSiswaNotFoundException();
        }
        deleteKelasSiswa(kelasSiswa);
    }

    @Override
    public void deleteKelasSiswa(KelasSiswa kelasSiswa) {
        kelasSiswaRepository.delete(kelasSiswa);
    }

    @Override
    public List<KelasSiswa> findKelasSiswaListByKelasId(Long id) {
        List<KelasSiswa> list = kelasSiswaRepository.findKelasSiswaListByKelasId(id);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public KelasSiswa setKelasSiswaRating(KelasRatingDTO kelasRatingDTO) {
        KelasSiswa kelasSiswa = kelasSiswaRepository.findKelasSiswaByKelasIdAndSiswaId(kelasRatingDTO.getKelasId(), kelasRatingDTO.getStudentId());
        if (kelasSiswa == null) {
            throw new KelasSiswaNotFoundException();
        }

        kelasSiswa.setRating(kelasRatingDTO.getRating());
        kelasSiswaRepository.save(kelasSiswa);
        return kelasSiswa;
    }



}
