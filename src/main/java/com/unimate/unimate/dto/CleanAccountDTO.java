package com.unimate.unimate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.AccountStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * DTO class for returning an Account Response without its password attribute
 */
@Data
@Accessors(chain = true)
public class CleanAccountDTO {
    @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String profilePicture;
    @NotNull
    private Role Role;
    @NotNull
    private AccountStatusEnum status;

    private Instant createdAt;

    private Instant modifiedAt;

    private Instant deletedAt;

    // this should be ignored
    @JsonIgnore
    private String password;
}
