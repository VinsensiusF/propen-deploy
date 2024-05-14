package com.unimate.unimate.repository;

import com.unimate.unimate.entity.KelasGuru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KelasGuruRepository extends JpaRepository<KelasGuru, Long> {
    @Query("SELECT kg FROM KelasGuru kg WHERE kg.kelas.id = :kelasId AND kg.guru.id = :guruId")
    KelasGuru findKelasGuruByKelasIdAndGuruId(@Param("kelasId") Long kelasId, @Param("guruId") Long guruId);

    @Query("SELECT kg FROM KelasGuru kg WHERE kg.kelas.id = :kelasId")
    List<KelasGuru> findKelasGuruListByKelasId(@Param("kelasId") Long kelasId);

    boolean existsByKelasIdAndGuruId(Long kelasId, Long guruId);
}