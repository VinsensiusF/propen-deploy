package com.unimate.unimate.repository;

import com.unimate.unimate.dto.KelasNameOnly;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Payment;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface KelasRepository extends JpaRepository<Kelas, Long> {
    Kelas findKelasById(Long id);
    List<KelasNameOnly> findKelasNameByIdNotNull();

    @Query("SELECT COUNT(e) FROM Kelas e WHERE e.deletedAt IS NULL")
    Long countAllClass();

   
}
