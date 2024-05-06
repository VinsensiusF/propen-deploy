package com.unimate.unimate.service;

import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.entity.Partnership;


import java.util.List;
import java.util.Map;

public interface PartnershipService {
    void savePartnership(Partnership partnership);

    void deletePartnership(Partnership partnership);

    List<Partnership> getAllPartnership();

    Partnership getPartnershipById(Long id);

    Partnership getPartnershipByIdUser(Long user);

    Partnership createPartnership(PartnershipDTO partnershipDTO);

    Partnership updatePartnership(UpdatePartnershipDTO updatePartnershipDTO);

    public List<Object[]> getMonthlyRequests(int year);

    public List<Map<String, Integer>> getDailyRequests(int year, int month);


    public Map<Integer, Integer> getMonthlyRequestsForAllYears();


    public List<Map<String, Object>> getRequestsByStatus(Integer year, Integer month);


}
