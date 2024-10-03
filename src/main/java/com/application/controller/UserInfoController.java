package com.application.controller;

import com.application.exception.UserNotFoundException;
import com.application.model.User;
import com.application.model.UserInfo;
import com.application.services.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin("*")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    private final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @PostMapping("/userinfo/saveuserinfo")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<String>saveuserinfo(@RequestBody UserInfo userInfo)
    {

        String usersave=userInfoService.usersave(userInfo);
        return new ResponseEntity<>(usersave,HttpStatus.CREATED);

    }

    @GetMapping("/userinfo/getalluserinfo")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<List<UserInfo>>getalluserinfo()
    {
        return  new ResponseEntity<>(userInfoService.getalluser(),HttpStatus.OK);
    }

    @GetMapping("/userinfo/getuserbyemail/{email}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<UserInfo>getuserbyemail(@PathVariable String email)
    {
        return new ResponseEntity<>(userInfoService.getUserByEmail(email),HttpStatus.OK);
    }


    @GetMapping("/userinfo/getUserById/{userId}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            if (userId == null) {
                logger.warn("User ID cannot be null");
                return ResponseEntity.badRequest().body("User ID cannot be null");
            }
            // Retrieve the user
            UserInfo userInfo = userInfoService.getuserbyid(userId);
            // Return the retrieved user
            return ResponseEntity.ok(userInfo);

        } catch (UserNotFoundException e) {
            logger.warn("User not found with ID: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving user with ID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/userinfo/delete/{userId}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            logger.info("Received request to delete user with ID: {}", userId);
            // Check if the user ID is null
            if (userId == null) {
                logger.warn("User ID cannot be null ");
                throw new UserNotFoundException("User ID cannot be null");
            }
            // Call the service method to delete the user
            userInfoService.deleteUser(userId);
            // Return a success response
            return ResponseEntity.ok("Successfully deleted user with ID: " + userId);

        } catch (UserNotFoundException e) {
            logger.warn("Deletion failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }



}