package com.application.services;

import com.application.model.ImageEntity;
import com.application.model.User;
import com.application.repository.ImageRepository;
import com.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ImageEntity saveProfileImageForUser(ImageEntity imageEntity, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        ImageEntity existingImage = imageRepository.findByUserEmail(userEmail);
        if (existingImage != null) {
            existingImage.setFileName(imageEntity.getFileName());
            existingImage.setImageData(imageEntity.getImageData());
        } else {
            existingImage = imageEntity;
        }

        existingImage.setUserEmail(userEmail);
        imageRepository.save(existingImage);

        return existingImage;
    }


    public byte[] getImageDataForUser(String userEmail) {
        ImageEntity imageEntity = imageRepository.findByUserEmail(userEmail);
        if (imageEntity != null) {
            return imageEntity.getImageData();
        } else {
            return null;
        }
    }
}
