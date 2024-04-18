package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class EditUserAccountDTO {
    @NotNull
    private String name;

    private String bio;

    private Date birthday;

    private String job;

    private String phoneNumber;

    private String address;
}
