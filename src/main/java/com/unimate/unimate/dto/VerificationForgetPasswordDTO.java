package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class VerificationForgetPasswordDTO {
    @NotNull
    private UUID token;
    @NotNull
    private String password;
    @NotNull
    private String repeatedPassword;
}
