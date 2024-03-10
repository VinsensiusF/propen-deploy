package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Kelas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KelasRepository extends JpaRepository<Kelas, Long> {
    Kelas findKelasById(Long id);
}
