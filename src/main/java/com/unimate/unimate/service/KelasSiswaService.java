package com.unimate.unimate.service;

import com.unimate.unimate.dto.KelasRatingDTO;
import com.unimate.unimate.dto.KelasSiswaDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasSiswa;

import java.util.List;

public interface KelasSiswaService {

    KelasSiswa enrollStudent(KelasSiswaDTO kelasSiswaDTO);

    void disenrollStudent(KelasSiswaDTO kelasSiswaDTO);

    KelasSiswa setKelasSiswaRating(KelasRatingDTO kelasRatingDTO);

    void deleteKelasSiswa(KelasSiswa kelasSiswa);

    List<KelasSiswa> findKelasSiswaListByKelasId(Long id);

    List<Kelas> getAllKelasEnrolledByStudent(Long studentId);

    List<Account> getAllStudentsInAClass(Long kelasId);

    Boolean isStudentEnrolledInAClass(Long kelasId, Long studentId);
}
