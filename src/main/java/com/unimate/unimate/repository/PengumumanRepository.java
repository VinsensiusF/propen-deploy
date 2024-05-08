package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Pengumuman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PengumumanRepository extends JpaRepository<Pengumuman, Long> {
    Pengumuman findPengumumanById(Long id);
    @Query("SELECT p FROM Pengumuman p WHERE p.kelas.id = :kelasId")
    List<Pengumuman> findPengumumanByKelasId(@Param("kelasId") Long kelasId);
}
