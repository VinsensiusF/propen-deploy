package com.unimate.unimate.dto;
import lombok.Data;


import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageDTO {
    private String email;
    private MultipartFile file;
    
}
