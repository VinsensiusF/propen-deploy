package com.unimate.unimate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
    
}
