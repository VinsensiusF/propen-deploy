package com.unimate.unimate.exception.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GeneralResponseDTO {
    private int code;
    private String message;
}
