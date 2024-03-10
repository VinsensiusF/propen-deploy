package com.unimate.unimate.repository;

import com.unimate.unimate.entity.KelasSiswa;
import com.unimate.unimate.entity.KelasSiswaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KelasSiswaRepository extends JpaRepository<KelasSiswa, Long> {
    @Query("SELECT ks FROM KelasSiswa ks WHERE ks.kelas.id = :kelasId AND ks.siswa.id = :siswaId")
    KelasSiswa findKelasSiswaByKelasIdAndSiswaId(@Param("kelasId") Long kelasId, @Param("siswaId") Long siswaId);

    @Query("SELECT ks FROM KelasSiswa ks WHERE ks.kelas.id = :kelasId")
    List<KelasSiswa> findKelasSiswaListByKelasId(@Param("kelasId") Long kelasId);
}
