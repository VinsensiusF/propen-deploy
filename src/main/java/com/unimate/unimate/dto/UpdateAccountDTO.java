package com.unimate.unimate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UpdateAccountDTO {
    @NotNull
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String profilePicture;
    @NotNull
    private String role;
//    @NotNull
//    private String status;
//    @NotNull
//    private String password;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Date birthday;
    @NotNull
    private String job;
    @NotNull
    private String bio;
}
