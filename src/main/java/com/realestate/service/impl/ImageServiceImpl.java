package com.realestate.service.impl;

import com.cloudinary.Cloudinary;
import com.realestate.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // ✅ FILE UPLOAD
    @Override
    public String uploadImage(MultipartFile file) {

        try {
            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of());

            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed");
        }
    }

    // ✅ URL UPLOAD (NEW)
    @Override
    public String uploadImageFromUrl(String imageUrl) {

        try {
            Map uploadResult = cloudinary.uploader()
                    .upload(imageUrl, Map.of());

            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Image upload from URL failed");
        }
    }
}

