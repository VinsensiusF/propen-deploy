package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.repository.PartnershipRepository;
import com.unimate.unimate.service.PartnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnershipServiceImpl implements PartnershipService {
    private final PartnershipRepository partnershipRepository;

    @Autowired
    public PartnershipServiceImpl(PartnershipRepository partnershipRepository){
        this.partnershipRepository = partnershipRepository;
    }

    @Override
    public void savePartnership(Partnership partnership) {
        partnershipRepository.save(partnership);
    }

    @Override
    public void deletePartnership(Partnership partnership) {
        partnershipRepository.delete(partnership);
    }

    @Override
    public List<Partnership> getAllPartnership() {
        return partnershipRepository.findAll();
    }

    @Override
    public Partnership getPartnershipById(Long id) {
        return partnershipRepository.findPartnershipById(id);
    }

    @Override
    public Partnership createPartnership(PartnershipDTO partnershipDTO) {
        Partnership partnership = new Partnership();
        partnership.setName(partnershipDTO.getName());
        partnership.setEmail(partnershipDTO.getEmail());
        partnership.setDescription(partnershipDTO.getDescription());
        savePartnership(partnership);
        return partnership;
    }

    @Override
    public Partnership updatePartnership(UpdatePartnershipDTO updatePartnershipDTO) {
        Partnership partnership = getPartnershipById(updatePartnershipDTO.getId());
        partnership.setName(updatePartnershipDTO.getName());
        partnership.setEmail(updatePartnershipDTO.getEmail());
        partnership.setDescription(updatePartnershipDTO.getDescription());
        savePartnership(partnership);
        return partnership;
    }
}
