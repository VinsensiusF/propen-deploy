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
import java.util.stream.Collectors;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Object[]> getMonthlyRequests(int year) {
        return partnershipRepository.countMonthlyRequests(year);
    }

 
    
    public List<Map<String, Integer>> getDailyRequests(int year, int month) {
        // Mendapatkan jumlah hari dalam bulan yang diberikan
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        
        List<Object[]> dailyRequests = partnershipRepository.countDailyRequests(year, month);
        
        // Inisialisasi list untuk menyimpan hasil permintaan harian
        List<Map<String, Integer>> dailyRequestsList = new ArrayList<>();
        
        // Inisialisasi map untuk menyimpan data permintaan harian
        Map<String, Integer> dailyRequestsMap;
        
        // Iterasi melalui hasil yang diterima dari repository
        for (int i = 1; i <= daysInMonth; i++) {
            dailyRequestsMap = new HashMap<>();
            dailyRequestsMap.put("date", i); // Tanggal
            dailyRequestsMap.put("count", 0); // Set jumlah permintaan untuk setiap hari menjadi 0 secara default
            dailyRequestsList.add(dailyRequestsMap);
        }
        
        // Isi list dengan hasil dari repository
        for (Object[] row : dailyRequests) {
            int day = (int) row[0]; // Day
            int requestCount = ((Number) row[1]).intValue(); // Request count
            
            // Update jumlah permintaan untuk hari tertentu
            dailyRequestsList.get(day - 1).put("count", requestCount);
        }
        
        return dailyRequestsList;
    }
    

    public List<Map<String, Object>> getRequestsByStatus(Integer year, Integer month) {
        try {
            List<Object[]> requestsByStatus;
            if (year != null && month != null) {
                requestsByStatus = partnershipRepository.countRequestsByStatus(year, month);
            } else if (year != null) {
                requestsByStatus = partnershipRepository.countRequestsByStatusForAllMonths(year.intValue());
            } else if (month != null) {
                requestsByStatus = partnershipRepository.countRequestsByStatusForAllYears(month.intValue());
            } else {
                requestsByStatus = partnershipRepository.countRequestsByStatusForAllYearsAndMonths();
            }
    
            // Calculate cumulative counts
            Map<PartnershipStatus, Integer> cumulativeCounts = new HashMap<>();
            for (Object[] row : requestsByStatus) {
                PartnershipStatus status = (PartnershipStatus) row[0];
                int count = ((Number) row[1]).intValue();
                cumulativeCounts.put(status, cumulativeCounts.getOrDefault(status, 0) + count);
            }
            
            // Convert to List of Map<String, Object>
            List<Map<String, Object>> result = new ArrayList<>();
            for (Map.Entry<PartnershipStatus, Integer> entry : cumulativeCounts.entrySet()) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("count", entry.getValue());
                obj.put("status", entry.getKey().toString());
                result.add(obj);
            }
    
            return result;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
    

    public Map<Integer, Integer> getMonthlyRequestsForAllYears() {
        try {
            List<Object[]> monthlyRequests = partnershipRepository.countMonthlyRequestsForAllYears();
            Map<Integer, Integer> monthlyRequestsMap = new HashMap<>();
            for (Object[] row : monthlyRequests) {
                int month = (int) row[0];
                int requestCount = ((Number) row[1]).intValue();
                monthlyRequestsMap.put(month, monthlyRequestsMap.getOrDefault(month, 0) + requestCount);
            }
            return monthlyRequestsMap;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }


}
