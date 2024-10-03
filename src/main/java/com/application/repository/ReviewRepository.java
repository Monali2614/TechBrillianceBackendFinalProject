package com.application.repository;

import com.application.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    public Review findByName(String name);
}
