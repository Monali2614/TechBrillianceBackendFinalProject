package com.application.services;

import com.application.exception.UserNotFoundException;
import com.application.model.Career;
import com.application.repository.CareerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;
    private final Logger logger = LoggerFactory.getLogger(CareerService.class);

    public String savecareer(Career career)
    {
        careerRepository.save(career);
        return "carreer saved succefully";
    }

    public Career getcarrerbyid(Long careerId)
    {
        return   careerRepository.findById(careerId) .orElseThrow(() -> {
            logger.warn("CareerDetail with ID {} not found ", careerId);
            return new UserNotFoundException("User with ID " + careerId + " not found");
        });
    }

    public List<Career>getallcarrerdetalis()
    {
        return careerRepository.findAll();
    }

}
