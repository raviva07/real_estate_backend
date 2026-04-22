package com.realestate.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile file);
    String uploadImageFromUrl(String imageUrl);
}
