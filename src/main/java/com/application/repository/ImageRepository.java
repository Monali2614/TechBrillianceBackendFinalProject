package com.application.repository;

import com.application.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    ImageEntity findByUserEmail(String userEmail);
}
