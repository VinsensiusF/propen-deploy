package com.unimate.unimate.restcontroller;

import com.unimate.unimate.aspect.ValidateToken;
import com.unimate.unimate.dto.*;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.KelasSiswa;
import com.unimate.unimate.enums.RoleEnum;
import com.unimate.unimate.exception.KelasNotFoundException;
import com.unimate.unimate.service.KelasService;
import com.unimate.unimate.service.KelasSiswaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kelas")
public class KelasRestController {
    private final KelasService kelasService;
    private final KelasSiswaService kelasSiswaService;

    @Autowired
    public KelasRestController(KelasService kelasService, KelasSiswaService kelasSiswaService) {
        this.kelasService = kelasService;
        this.kelasSiswaService = kelasSiswaService;
    }

    @GetMapping("/get-all")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public List<Kelas> getAllKelas() {
        return kelasService.getAllKelas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findKelasById(@PathVariable("id") Long id) {
        Kelas kelas = kelasService.getKelasById(id);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        return ResponseEntity.ok(kelas);
    }

    @PostMapping("/create")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<Kelas> createKelas(@Valid @RequestBody CreateKelasDTO createKelasDTO) {
        Kelas kelas = kelasService.createKelas(createKelasDTO);
        return ResponseEntity.ok(kelas);
    }

    @PutMapping("/update")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<Kelas> updateKelas(@Valid @RequestBody UpdateKelasDTO updateKelasDTO) {
        Kelas kelas = kelasService.updateKelas(updateKelasDTO);
        return ResponseEntity.ok(kelas);
    }

    @DeleteMapping("/{id}")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<String> deleteKelas(@PathVariable("id") Long id) {
        Kelas kelas = kelasService.getKelasById(id);
        if (kelas == null) {
            throw new KelasNotFoundException();
        }
        List<KelasSiswa> kelasSiswaList = kelasSiswaService.findKelasSiswaListByKelasId(id);

        for (KelasSiswa ks :
                kelasSiswaList) {
            kelasSiswaService.deleteKelasSiswa(ks);
        }

        kelasService.deleteKelas(kelas);

        return ResponseEntity.ok("Kelas has been deleted successfully.");


    }

    @PostMapping("/enroll")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> enrollStudent(@RequestBody KelasSiswaDTO kelasSiswaDTO) {
        KelasSiswa kelasSiswa = kelasSiswaService.enrollStudent(kelasSiswaDTO);
        return ResponseEntity.ok(kelasSiswa);
    }

    @PostMapping("/disenroll")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> disenrollStudent(@RequestBody KelasSiswaDTO kelasSiswaDTO) {
        kelasSiswaService.disenrollStudent(kelasSiswaDTO);
        return ResponseEntity.ok("Siswa has been successfully disenrolled from Kelas");
    }

    @GetMapping("/enrolled-students/{id}")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> getEnrolledStudents(@PathVariable("id") Long kelasId) {
        List<Account> enrolledStudents = kelasSiswaService.getAllStudentsInAClass(kelasId);
        return ResponseEntity.ok(enrolledStudents);
    }

    @GetMapping("/classes-enrolled/{id}")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> getClassesEnrolled(@PathVariable("id") Long siswaId) {
        List<Kelas> classesEnrolled = kelasSiswaService.getAllKelasEnrolledByStudent(siswaId);
        return ResponseEntity.ok(classesEnrolled);
    }

    @GetMapping("/is-enrolled")
    @ValidateToken({RoleEnum.STUDENT, RoleEnum.ADMIN, RoleEnum.TEACHER, RoleEnum.TOP_LEVEL, RoleEnum.CUSTOMER_SERVICE})
    public ResponseEntity<?> isStudentEnrolled(@RequestParam(required = true, name = "kelasId") Long kelasId, @RequestParam(required = true, name = "siswaId") Long siswaId) {
        Boolean isEnrolled = kelasSiswaService.isStudentEnrolledInAClass(kelasId, siswaId);
        return ResponseEntity.ok(isEnrolled);
    }


    @PostMapping("/rating")
    @ValidateToken(RoleEnum.ADMIN)
    public ResponseEntity<?> setRatingFromSiswa(@RequestBody KelasRatingDTO kelasRatingDTO) {
        // Siswa gives new rating
        KelasSiswa kelasSiswa = kelasSiswaService.setKelasSiswaRating(kelasRatingDTO);

        // Kelas recalculates its ratings
        List<KelasSiswa> kelasSiswaList = kelasSiswaService.findKelasSiswaListByKelasId(kelasRatingDTO.getKelasId());


        int ratings = 0;
        int ratingsCount = 0;
        for (KelasSiswa ks :
                kelasSiswaList) {
            if (ks.getRating() == null) {
                continue;
            }
            ratings += ks.getRating();
            ratingsCount++;
        }

        Kelas kelas = kelasService.updateRating(kelasRatingDTO.getKelasId(), (float) ratings / (float) ratingsCount);

        return ResponseEntity.ok(kelas);

    }

    @PostMapping("assign-teacher")
    public ResponseEntity<?> assignTeacherToKelas(@RequestBody SetTeacherDTO setTeacherDTO) {
        Kelas kelas = kelasService.setTeacherToKelas(setTeacherDTO);
        return ResponseEntity.ok(kelas);
    }
}
