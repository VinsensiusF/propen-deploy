package com.unimate.unimate.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.unimate.unimate.service.CloudinaryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            if (!file.getContentType().startsWith("image")) {
                throw new IllegalArgumentException("File harus berupa gambar.");
            }
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folderName);

            Map<String, Object> uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");

            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteFileByUrl(String imageUrl, String folderName) {
        String publicId = extractPublicId(imageUrl);
        if (publicId != null) {
            String pointer = folderName + "/" + publicId;
            ApiResponse apiResponse = null; 
            try {
                apiResponse = cloudinary.api().deleteResources(
                    Arrays.asList(pointer),
                    ObjectUtils.asMap(
                        "type", "upload",
                        "resource_type", "image"
                    )
                );
                System.out.println(apiResponse);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        } else {
            System.out.println("Public ID tidak dapat diekstrak dari URL.");
        }
    }


    private String extractPublicId(String imageUrl) {
            String[] parts = imageUrl.split("/");
            String lastPart = parts[parts.length - 1];
            String[] idParts = lastPart.split("\\.");
        return idParts[0];
    }

}