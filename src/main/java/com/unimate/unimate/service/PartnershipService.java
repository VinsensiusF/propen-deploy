package com.unimate.unimate.service;

import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Partnership;

import java.util.List;

public interface PartnershipService {
    void savePartnership(Partnership partnership);

    List<Partnership> getAllPartnership();

    Partnership getPartnershipById(Long id);
}
