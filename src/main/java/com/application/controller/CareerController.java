package com.application.controller;


import com.application.model.Career;
import com.application.services.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CareerController {

    @Autowired
    private CareerService careerService;



    @PostMapping("/carrer/savecareerdetails")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<String>savecareer (
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("workExperience") String workExperience,
            @RequestParam("desiredDomain") String desiredDomain,
            @RequestParam("resume") MultipartFile resumeFile
    )throws IOException
    {
        Career career=new Career();
        career.setName(name);
        career.setEmail(email);
        career.setContactNumber(contactNumber);
        career.setWorkExperience(workExperience);
        career.setDesiredDomain(desiredDomain);
        career.setResume(resumeFile.getBytes());
        return new ResponseEntity<>(careerService.savecareer(career),HttpStatus.CREATED);
    }



    @GetMapping("/carrer/getcarrerdetailsbyid/{careerId}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<Career>getcarerbyid(Long careerId)
    {
        return new ResponseEntity<>(careerService.getcarrerbyid(careerId), HttpStatus.OK);
    }


    @GetMapping("/carrer/getcarrerdetailsofall")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<List<Career>>getallcarerdetails()
    {
        return new ResponseEntity<>(careerService.getallcarrerdetalis(),HttpStatus.OK);
    }

    @GetMapping("/carrer/download/{careerId}")
    @CrossOrigin(origins = LoginController.ApiURL)
    public ResponseEntity<byte[]> downloadCareer(@PathVariable Long careerId) {
        Career career = careerService.getcarrerbyid(careerId);
        if (career != null && career.getResume() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "resume_" + careerId + ".pdf");
            return new ResponseEntity<>(career.getResume(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}