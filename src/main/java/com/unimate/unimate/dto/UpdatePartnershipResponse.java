package com.unimate.unimate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.unimate.unimate.entity.Partnership;

@Data
@AllArgsConstructor
public class UpdatePartnershipResponse {
    private Partnership partnership;
    private String message;
}
