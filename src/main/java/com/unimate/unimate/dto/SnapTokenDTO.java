package com.unimate.unimate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SnapTokenDTO {
    private String token;
}
