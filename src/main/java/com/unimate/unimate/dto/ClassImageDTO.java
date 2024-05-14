package com.unimate.unimate.dto;
import lombok.Data;


import org.springframework.web.multipart.MultipartFile;

@Data
public class ClassImageDTO {
    private long id;
    private MultipartFile file;
    
}
