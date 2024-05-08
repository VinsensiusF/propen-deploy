package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Ujian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UjianRepository extends JpaRepository<Ujian, Long> {
    Ujian findUjianById(Long id);

    List<Ujian> findAllByKelas(Kelas kelas);

    @Query("SELECT u FROM Ujian u WHERE u.kelas.id = :kelasId")
    List<Ujian> findUjianListByKelasId(@Param("kelasId") Long kelasId);
}
