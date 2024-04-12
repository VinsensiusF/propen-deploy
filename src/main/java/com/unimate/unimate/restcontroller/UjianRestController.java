package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UjianDTO;
import com.unimate.unimate.dto.UpdateUjianDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.UjianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ujian")
public class UjianRestController {
    private final UjianService ujianService;

    @Autowired
    public UjianRestController(UjianService ujianService){
        this.ujianService = ujianService;
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<List<Ujian>> findAllUjian() {
        return ResponseEntity.ok(ujianService.getAllUjian());
    }

    @GetMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.STUDENT, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> findUjianById(@PathVariable("id") Long id) {
        Ujian ujian = ujianService.getUjianById(id);
        if (ujian == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ujian is not found.");
        }

        return ResponseEntity.ok(ujian);
    }

    @PostMapping("/create")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> createUjian(@Valid @RequestBody UjianDTO ujianDTO){
        Ujian ujian = ujianService.createUjian(ujianDTO);
        if (ujian != null) {
            return ResponseEntity.ok(ujian);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Ujian.");
        }
    }

    @PutMapping("/update")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<?> updateUjian(@Valid @RequestBody UpdateUjianDTO updateUjianDTO) {
        Ujian ujian = ujianService.updateUjian(updateUjianDTO);
        return ResponseEntity.ok(ujian);
    }

    @DeleteMapping("/{id}")
    @ValidateToken({RoleEnum.ADMIN,RoleEnum.TEACHER})
    public ResponseEntity<?> deleteUjian(@PathVariable("id") Long id) {
        Ujian ujian = ujianService.getUjianById(id);
        if (ujian == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ujian is not found.");
        }
        ujianService.deleteUjian(ujian);
        return ResponseEntity.ok("Ujian has been successfully deleted.");
    }
}
