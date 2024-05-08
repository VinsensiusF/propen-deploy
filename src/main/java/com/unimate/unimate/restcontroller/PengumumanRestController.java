package com.unimate.unimate.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.CreatePengumumanDTO;
import com.unimate.unimate.dto.UpdatePartnershipResponse;
import com.unimate.unimate.dto.UpdatePengumumanDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.Pengumuman;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.PengumumanNotFoundException;
import com.unimate.unimate.service.PengumumanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pengumuman")
public class PengumumanRestController {
    private final PengumumanService pengumumanService;

    @Autowired
    public PengumumanRestController(PengumumanService pengumumanService) {
        this.pengumumanService = pengumumanService;
    }

    @GetMapping("/class-pengumuman/{kelasId}")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<List<Pengumuman>> getPengumumanByKelasId(@PathVariable("kelasId") Long kelasId) {
        List<Pengumuman> pengumumanInAClass = pengumumanService.getPengumumanByKelasId(kelasId);
        return ResponseEntity.ok(pengumumanInAClass);
    } 

    @GetMapping("/{id}")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> getPengumumanById(@PathVariable("id") Long id) {
        Pengumuman pengumuman = pengumumanService.getPengumumanById(id);
        if (pengumuman == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pengumuman not found.");
        return ResponseEntity.ok(pengumuman);
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.TEACHER)
    public ResponseEntity<?> createPengumuman(@Valid @RequestBody CreatePengumumanDTO createPengumumanDTO) {
        Pengumuman pengumuman = pengumumanService.createPengumuman(createPengumumanDTO);
        return ResponseEntity.ok(pengumuman);
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.TEACHER)
    public ResponseEntity<?> updatePengumuman(@RequestBody UpdatePengumumanDTO updatePengumumanDTO) {
        try {
            Pengumuman pengumuman = pengumumanService.updatePengumuman(updatePengumumanDTO);
            return ResponseEntity.ok(pengumuman);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update pengumuman request.");
        }
    }

    @DeleteMapping("/{id}")
    @ValidateToken(RoleEnum.TEACHER)
    public ResponseEntity<?> deletePengumuman(@PathVariable("id") Long id) {
        Pengumuman pengumuman = pengumumanService.getPengumumanById(id);
        if (pengumuman == null) {
            throw new PengumumanNotFoundException();
        }
        pengumumanService.deletePengumuman(pengumuman);
        return ResponseEntity.ok("Pengumuman has been successfully deleted");
    }
}
