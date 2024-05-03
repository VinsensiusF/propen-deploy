package com.unimate.unimate.service.impl;

import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.PartnershipStatus;
import com.unimate.unimate.repository.PartnershipRepository;
import com.unimate.unimate.service.PartnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;

@Service
public class PartnershipServiceImpl implements PartnershipService {
    private final PartnershipRepository partnershipRepository;

    private final AccountServiceImpl accountService;

    @Autowired
    public PartnershipServiceImpl(PartnershipRepository partnershipRepository, AccountServiceImpl accountService){
        this.partnershipRepository = partnershipRepository;
        this.accountService = accountService;
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
        partnership.setCompany(partnershipDTO.getCompany());
        partnership.setCompanyEmail(partnershipDTO.getCompanyEmail());
        partnership.setDescription(partnershipDTO.getDescription());
        partnership.setPhoneNumber(partnershipDTO.getPhoneNumber());

        Account user = accountService.getAccountByIdUser(partnershipDTO.getUser());
        partnership.setUser(user);
        
        savePartnership(partnership);
        return partnership;
    }

    @Override
public Partnership updatePartnership(UpdatePartnershipDTO updatePartnershipDTO) {
    Partnership partnership = getPartnershipById(updatePartnershipDTO.getId());

    // Handling status using if-else conditional
    PartnershipStatus newStatus;
    switch (updatePartnershipDTO.getStatus()) {
        case 1:
            newStatus = PartnershipStatus.NEW;
            break;
        case 2:
            newStatus = PartnershipStatus.RUNNING;
            break;
        case 3:
            newStatus = PartnershipStatus.CANCELED;
            break;
        case 4:
            newStatus = PartnershipStatus.CONFIRMED;
            break;
        case 5:
            newStatus = PartnershipStatus.COMPLETED;
            break;
        default:
            throw new IllegalArgumentException("Invalid status code");
    }
    partnership.setStatus(newStatus);
    savePartnership(partnership);
    
    return partnership;
}

    @Override
    public Partnership getPartnershipByIdUser(Long user) {
        // TODO Auto-generated method stub
        Account userAccount = accountService.getAccountByIdUser(user);
        return partnershipRepository.findPartnershipByUser(userAccount);
       
    }

}
