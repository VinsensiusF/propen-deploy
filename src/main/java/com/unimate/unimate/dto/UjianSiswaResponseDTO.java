package com.unimate.unimate.dto;

import com.unimate.unimate.entity.Account;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UjianSiswaResponseDTO {
    private Long id;
    private Long ujianId;
    private Account siswa;
    private Double grade;
}
