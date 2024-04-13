package com.unimate.unimate.repository;

import com.unimate.unimate.entity.KelasSiswa;
import com.unimate.unimate.entity.UjianSiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UjianSiswaRepository extends JpaRepository<UjianSiswa, Long> {
    @Query("SELECT us FROM UjianSiswa us WHERE us.ujian.id = :ujianId AND us.siswa.id = :siswaId")
    UjianSiswa findUjianSiswaByUjianIdAndSiswaId(@Param("ujianId") Long ujianId, @Param("siswaId") Long siswaId);

    @Query("SELECT us FROM UjianSiswa us WHERE us.ujian.id = :ujianId")
    List<UjianSiswa> findUjianSiswaListByUjianId(@Param("ujianId") Long ujianId);

    @Query("SELECT us FROM UjianSiswa us WHERE us.siswa.id = :siswaId")
    List<UjianSiswa> findUjianSiswaListBySiswaId(@Param("siswaId") Long siswaId);
}
