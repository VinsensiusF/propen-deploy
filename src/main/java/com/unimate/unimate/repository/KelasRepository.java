package com.unimate.unimate.repository;

import com.unimate.unimate.dto.KelasNameOnly;
import com.unimate.unimate.entity.Kelas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface KelasRepository extends JpaRepository<Kelas, Long> {
    Kelas findKelasById(Long id);
    List<KelasNameOnly> findKelasNameByIdNotNull();
}
