package com.unimate.unimate.service;

import com.unimate.unimate.dto.CreateKelasDTO;
import com.unimate.unimate.dto.KelasNameOnly;
import com.unimate.unimate.dto.SetTeacherDTO;
import com.unimate.unimate.dto.UpdateKelasDTO;
import com.unimate.unimate.entity.Kelas;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface KelasService {
    void saveKelas(Kelas kelas);

    List<Kelas> getAllKelas();

    Kelas getKelasById(Long id);

    Kelas createKelas(CreateKelasDTO createKelasDTO);

    Kelas updateKelas(UpdateKelasDTO updateKelasDTO);

    void deleteKelas(Kelas kelas);

    Kelas updateRating(Long idKelas, Float newRating);

    List<KelasNameOnly> getAllKelasNames();

    Long getCountClass();

    ResponseEntity<Map> uploadImageClass(MultipartFile file, long id);
    
    ResponseEntity<Map> uploadCoverClass(MultipartFile file, long id);
}
