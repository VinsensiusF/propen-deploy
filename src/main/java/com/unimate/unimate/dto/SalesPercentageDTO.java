package com.unimate.unimate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SalesPercentageDTO {
    private double centage;
    private Boolean status;
}
