package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class SignUpDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
}
