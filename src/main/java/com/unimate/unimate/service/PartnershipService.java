package com.unimate.unimate.service;

import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.entity.Partnership;

import java.util.List;

public interface PartnershipService {
    void savePartnership(Partnership partnership);

    void deletePartnership(Partnership partnership);

    List<Partnership> getAllPartnership();

    Partnership getPartnershipById(Long id);

    Partnership createPartnership(PartnershipDTO partnershipDTO);

    Partnership updatePartnership(UpdatePartnershipDTO updatePartnershipDTO);
}
