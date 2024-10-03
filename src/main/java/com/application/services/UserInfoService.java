package com.application.services;


import com.application.exception.UserNotFoundException;
import com.application.model.UserInfo;
import com.application.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {


    @Autowired
    private UserInfoRepository userInfoRepository;

    private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);



    public String usersave(UserInfo userInfo)
    {
        userInfoRepository.save(userInfo);
        return "user saved succesfully............";
    }

    public List<UserInfo>getalluser()
    {
        return   userInfoRepository.findAll();
    }

    public UserInfo getuserbyid(Long userId)
    {
        try {
            logger.info("Retrieving user with ID: {}", userId);
            // Retrieve the user by ID, or throw an exception if not found
            return userInfoRepository.findById(userId)
                    .orElseThrow(() -> {
                        logger.warn("User with ID {} not found ", userId);
                        return new UserNotFoundException("User with ID " + userId + " not found");
                    });
        } catch (Exception e) {
            logger.error("An error occurred while retrieving user with ID: {}", userId, e);
            throw new RuntimeException("Failed to retrieve user", e);
        }
    }

    public void deleteUser(Long userId) {
        try {
            logger.info("Attempting to delete user with ID: {}", userId);

            userInfoRepository.deleteById(userId);

            logger.info("Successfully deleted user with ID: {}", userId);

        } catch (UserNotFoundException e) {
            logger.error("Deletion failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while deleting user with ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    public UserInfo getUserByEmail(String email) throws UserNotFoundException {
        return userInfoRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }



}
