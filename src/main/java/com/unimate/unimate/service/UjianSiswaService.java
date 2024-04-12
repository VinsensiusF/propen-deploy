package com.unimate.unimate.service;

import com.unimate.unimate.dto.UjianSiswaDTO;
import com.unimate.unimate.entity.UjianSiswa;

import java.util.List;

public interface UjianSiswaService {

    void saveUjianSiswa(UjianSiswa ujianSiswa);
    void deleteUjianSiswa(UjianSiswa ujianSiswa);

    UjianSiswa takeExam(UjianSiswaDTO ujianSiswaDTO);
    void abandonExam(UjianSiswaDTO ujianSiswaDTO);
    List<UjianSiswa> getUjianSiswaByUjianId(Long ujianId);
    List<UjianSiswa> getUjianSiswaBySiswaId(Long siswaId);
    UjianSiswa getUjianSiswaByUjianIdAndSiswaId(Long ujianId, Long siswaId);
    UjianSiswa gradeExam(UjianSiswaDTO ujianSiswaDTO, Integer grade);


}
