package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipResponse;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.PartnershipStatus;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.PartnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/partnership")
public class PartnershipRestController {
    private final PartnershipService partnershipService;

    @Autowired
    public PartnershipRestController (PartnershipService partnershipService){
        this.partnershipService = partnershipService;
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.STUDENT)
    public ResponseEntity<String> createPartnership(@RequestBody PartnershipDTO partnershipDTO){
        try {
            // Check if there is an existing partnership request from the same user
            Partnership existingPartnership = partnershipService.getPartnershipByIdUser(partnershipDTO.getUser());
            
            // If there is an existing partnership request
            if (existingPartnership != null) {
                Date now = new Date();
                long diffInMillies = Math.abs(now.getTime() - existingPartnership.getCreatedAt().getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                
                // If the existing request is created within one day, reject the new request
                if (diffInDays < 1) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Anda hanya dapat melakukan permintaan mitra sekali dalam 1 hari, selahkan kirim permintaan pada hari selanjutnya");
                }
            }

            Partnership partnership = partnershipService.createPartnership(partnershipDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Permintaan mitra partnership berhasil dikirimkan, Admin kami akan segera menghubungi Anda.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create partnership request.");
        }
    }


    @GetMapping("/get-all")
    @ValidateToken(RoleEnum.CUSTOMER_SERVICE)
    public List<Partnership> getAllPartnership(){
        return partnershipService.getAllPartnership();
    }

    @DeleteMapping("/delete")
    @ValidateToken(RoleEnum.CUSTOMER_SERVICE)
    public ResponseEntity<String> deletePartnership(@RequestParam Long id){
        try {
            Partnership partnership = partnershipService.getPartnershipById(id);
            partnershipService.deletePartnership(partnership);
            return ResponseEntity.status(HttpStatus.OK).body("Partnership Request has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete partnership request.");
        }
    }


    @PutMapping("/update")
    @ValidateToken(RoleEnum.CUSTOMER_SERVICE)
    public ResponseEntity<?> updatePartnership(@RequestBody UpdatePartnershipDTO updatePartnershipDTO){
        try {
            Partnership partnership = partnershipService.updatePartnership(updatePartnershipDTO);
            String message = "Partnership Request has been updated successfully.";
            return ResponseEntity.ok().body(new UpdatePartnershipResponse(partnership, message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update partnership request.");
        }
    }

   
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyRequests(@RequestParam("year") int year) {
        try {
            List<Object[]> monthlyRequests = partnershipService.getMonthlyRequests(year);

            // Proses hasil kueri untuk mengonversi ke format yang diinginkan
            Map<Integer, Integer> monthlyRequestsMap = new HashMap<>();
            for (Object[] row : monthlyRequests) {
                int month = (int) row[0];
                long requestCount = (long) row[1];
                monthlyRequestsMap.put(month, (int) requestCount);
            }

            return ResponseEntity.ok(monthlyRequestsMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve monthly requests: " + e.getMessage());
        }
    }

    @GetMapping("/all-monthly")
    public ResponseEntity<Map<Integer, Integer>> getMonthlyRequestsForAllYears() {
        try {
            Map<Integer, Integer> monthlyRequests = partnershipService.getMonthlyRequestsForAllYears();
            return ResponseEntity.ok(monthlyRequests);
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/daily")
    public ResponseEntity<List<Map<String, Integer>>> getDailyRequests(
            @RequestParam("year") int year,
            @RequestParam("month") int month
    ) {
        try {
            List<Map<String, Integer>> dailyRequests = partnershipService.getDailyRequests(year, month);
            return ResponseEntity.ok(dailyRequests);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @GetMapping("/status")
    public ResponseEntity<List<Map<String, Object>>> getRequestsByStatus(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {
        try {
            List<Map<String, Object>> requestsByStatus = partnershipService.getRequestsByStatus(year, month);
            return ResponseEntity.ok(requestsByStatus);
        } catch (Exception e) {
            // Log the exception here
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
      
      


}
