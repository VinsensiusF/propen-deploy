package com.unimate.unimate.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    public String uploadFile(MultipartFile file, String folderName);

    public void deleteFileByUrl(String imageUrl, String folderName);

    
} 