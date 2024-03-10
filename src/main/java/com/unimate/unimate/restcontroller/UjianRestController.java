package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.PartnershipDTO;
import com.unimate.unimate.dto.UjianDTO;
import com.unimate.unimate.entity.Partnership;
import com.unimate.unimate.entity.Ujian;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.service.UjianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ujian")
public class UjianRestController {
    private final UjianService ujianService;

    @Autowired
    public UjianRestController(UjianService ujianService){
        this.ujianService = ujianService;
    }

    @PostMapping("/create")
    @ValidateToken({RoleEnum.ADMIN, RoleEnum.TEACHER})
    public ResponseEntity<String> createUjian(@RequestBody UjianDTO ujianDTO){
        Ujian ujian = ujianService.createUjian(ujianDTO);
        if (ujian != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Ujian has been created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Ujian.");
        }
    }
}
