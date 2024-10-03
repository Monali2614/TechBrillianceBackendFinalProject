package com.application.controller;

import com.application.model.ImageEntity;
import com.application.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin("*")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadimage/{userEmail}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public String uploadProfileImageForUser(@RequestParam("file") MultipartFile file, @PathVariable String userEmail) {
        try {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setFileName(file.getOriginalFilename());
            imageEntity.setImageData(file.getBytes());
            imageService.saveProfileImageForUser(imageEntity, userEmail);
            return "Profile image uploaded successfully for user with email: " + userEmail;
        } catch (IOException e) {
            return "Failed to upload profile image: " + e.getMessage();
        }
    }

    @GetMapping("/getprofile/{userEmail}")
    @CrossOrigin(origins = LoginController.ApiURL)

    public ResponseEntity<byte[]> getImageForUser(@PathVariable String userEmail) {
        byte[] imageData = imageService.getImageDataForUser(userEmail);
        if (imageData != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imageData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

