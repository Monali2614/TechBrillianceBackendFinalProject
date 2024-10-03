package com.application.controller;

import com.application.model.Review;
import com.application.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.application.model.Professor;
import com.application.model.User;
import com.application.services.ProfessorService;
import com.application.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController
{

	public static  final String ApiURL="*";
	@Autowired
	private UserService userService;

	@Autowired
	private ProfessorService professorService;

	@GetMapping("/")
	public String welcomeMessage()
	{
		return "Welcome to Elearning Management system !!!";
	}

	@PostMapping("/loginuser")
	@CrossOrigin(origins = ApiURL)
	public User loginUser(@RequestBody User user) throws Exception
	{
		String currEmail = user.getEmail();

		String currPassword = RegistrationController.encrypt(user.getPassword());
		System.out.println(currPassword);

		User userObj = null;
		if(currEmail != null && currPassword != null)
		{
			userObj = userService.fetchUserByEmailAndPassword(currEmail, currPassword);
			userObj.setPassword(RegistrationController.decrypt(userObj.getPassword()));
		}
		if(userObj == null)
		{
			throw new Exception("User does not exists!!! Please enter valid credentials...");
		}
		return userObj;
	}

	@PostMapping("/loginprofessor")
	@CrossOrigin(origins = ApiURL)
	public Professor loginDoctor(@RequestBody Professor professor) throws Exception
	{
		String currEmail = professor.getEmail();
		String currPassword = RegistrationController.encrypt(professor.getPassword());

		Professor professorObj = null;
		if(currEmail != null && currPassword != null)
		{
			professorObj = professorService.fetchProfessorByEmailAndPassword(currEmail, currPassword);
			professorObj.setPassword(RegistrationController.decrypt(professorObj.getPassword()));
		}
		if(professorObj == null)
		{
			throw new Exception("Professor does not exists!!! Please enter valid credentials...");
		}
		return professorObj;
	}

	@PostMapping("/addReview")
	@CrossOrigin(origins = ApiURL)
	public String addReview(@RequestBody Review review) {
		if(review==null){
			System.out.println("null");
		}
			userService.addReview(review);
			return "Review added successfully";
	}

	@GetMapping("/getallreviews")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Review>> getAllReviews() throws Exception
	{
		List<Review> rev = userService.getAllReview();
		return new ResponseEntity<List<Review>>(rev, HttpStatus.OK);
	}


}