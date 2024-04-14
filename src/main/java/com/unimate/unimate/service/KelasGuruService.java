package com.unimate.unimate.service;

import com.unimate.unimate.dto.KelasGuruDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasGuru;
import com.unimate.unimate.entity.KelasSiswa;

import java.util.List;

public interface KelasGuruService {

    void createKelasGuru(KelasGuru kelasGuru);
    void deleteKelasGuru(KelasGuru kelasGuru);

    KelasGuru assignGuru(KelasGuruDTO kelasGuruDTO);
    void unassignGuru(KelasGuruDTO kelasGuruDTO);

    List<KelasGuru> findKelasGuruListByKelasId(Long kelasId);
    KelasGuru findKelasGuruByKelasIdAndGuruId(Long kelasId, Long guruId);
    List<Account> findAllGuruInAClass(Long kelasId);
    List<Kelas> findAllKelasTaughtByAGuru(Long guruId);


}
