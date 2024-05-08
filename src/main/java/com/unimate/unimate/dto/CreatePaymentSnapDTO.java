package com.unimate.unimate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreatePaymentSnapDTO {
    private long course;
    private int price;
    private String token;

}
