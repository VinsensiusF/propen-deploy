package com.unimate.unimate.service;

import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.repository.PartnershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnershipServiceImpl implements PartnershipService{
    @Autowired
    private PartnershipRepository partnershipRepository;

    @Override
    public void savePartnership(Partnership partnership) {
        partnershipRepository.save(partnership);
    }

    @Override
    public List<Partnership> getAllPartnership() {
        return partnershipRepository.findAll();
    }

    @Override
    public Partnership getPartnershipById(Long id) {
        return partnershipRepository.findPartnershipById(id);
    }
}
