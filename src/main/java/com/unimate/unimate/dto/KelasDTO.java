package com.unimate.unimate.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KelasDTO {
    private long id;
    private String name;
    private long price;
    private String category;
    private String cover;
}
