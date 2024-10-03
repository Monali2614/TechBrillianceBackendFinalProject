package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.application.model.Professor;
import com.application.model.User;
import com.application.services.ProfessorService;
import com.application.services.UserService;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RegistrationController
{
	@Autowired
	private UserService userService;

	@Autowired
	private ProfessorService professorService;
	private static final String key = "AESEncryptionKey"; // 128 bit key
	private static final String transformation = "AES";

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static String encrypt(String plainText) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), transformation);
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decrypt(String encryptedText) throws Exception {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), transformation);
			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
			return new String(decryptedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/registeruser")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
		String currEmail = user.getEmail();
		String newID = getNewID();
		user.setUserid(newID);
		String password=encrypt(user.getPassword());
		user.setPassword(password);


		if (isValidEmail(user.getEmail()) == false) {
			return new ResponseEntity<>("enter valid userId", HttpStatus.BAD_REQUEST);
		}
		if (currEmail != null || !"".equals(currEmail)) {
			User userObj = userService.fetchUserByEmail(currEmail);
			if (userObj != null) {
				throw new Exception("User with " + currEmail + " already exists !!!");
			}
		}
		User userObj = null;
		userObj = userService.saveUser(user);
		return new ResponseEntity<>(userObj, HttpStatus.CREATED);
	}


	@PostMapping("/registerprofessor")
	@CrossOrigin(origins = LoginController.ApiURL)
	public Professor registerDoctor(@RequestBody Professor professor) throws Exception
	{
		String currEmail = professor.getEmail();
		String newID = getNewID();
		professor.setProfessorid(newID);
		String password=encrypt(professor.getPassword());
		professor.setPassword(password);

		if(currEmail != null || !"".equals(currEmail))
		{
			Professor professorObj = professorService.fetchProfessorByEmail(currEmail);
			if(professorObj != null)
			{
				throw new Exception("Professor with "+currEmail+" already exists !!!");
			}
		}
		Professor professorObj = null;
		professorObj = professorService.saveProfessor(professor);
		return professorObj;
	}

	public String getNewID()
	{
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 12; i++)
		{
			int index = (int)(AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}
}