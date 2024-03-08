package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UpdatePartnershipDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.PartnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partnership")
public class PartnershipRestController {
    private final PartnershipService partnershipService;

    @Autowired
    public PartnershipRestController (PartnershipService partnershipService){
        this.partnershipService = partnershipService;
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> createPartnership(@RequestBody PartnershipDTO partnershipDTO){
        Partnership partnership = partnershipService.createPartnership(partnershipDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Partnership Request has been created successfully.");
    }

    @GetMapping("/get-all")
    @ValidateToken(RoleEnum.ADMIN)
    public List<Partnership> getAllPartnership(){
        return partnershipService.getAllPartnership();
    }

    @DeleteMapping("/delete")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> deleteBlog(@RequestParam Long id){
        Partnership partnership = partnershipService.getPartnershipById(id);
        partnershipService.deletePartnership(partnership);
        return ResponseEntity.status(HttpStatus.OK).body("Partnership Request has been deleted successfully.");
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> updatePartnership(@RequestBody UpdatePartnershipDTO updatePartnershipDTO){
        Partnership blogog = partnershipService.updatePartnership(updatePartnershipDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Partnership Request has been updated successfully.");
    }
}
