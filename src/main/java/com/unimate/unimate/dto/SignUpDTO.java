package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SignUpDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
}
