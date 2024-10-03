package com.application.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.application.model.Chapter;
import com.application.model.Course;
import com.application.model.Professor;
import com.application.model.User;
import com.application.model.Wishlist;
import com.application.services.ChapterService;
import com.application.services.CourseService;
import com.application.services.ProfessorService;
import com.application.services.WishlistService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProfessorController
{	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping("/professorlist")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Professor>> getProfessorList() throws Exception
	{
		List<Professor> professors = professorService.getAllProfessors();
		return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
	}
	
	@GetMapping("/youtubecourselist")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Course>> getYoutubeCourseList() throws Exception
	{
		List<Course> youtubeCourseList = courseService.fetchByCoursetype("Youtube");
//		for(Course list:youtubeCourseList)
//		{
//			System.out.println(list.getYoutubeurl());
//		}
		return new ResponseEntity<List<Course>>(youtubeCourseList, HttpStatus.OK);
	}
	
	@GetMapping("/websitecourselist")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Course>> getWebsiteCourseList() throws Exception
	{
		List<Course> websiteCourseList = courseService.fetchByCoursetype("Website");
		return new ResponseEntity<List<Course>>(websiteCourseList, HttpStatus.OK);
	}

	@GetMapping("/googledrivelist")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Course>> getDriveCourseList() throws Exception
	{
		List<Course> driveList = courseService.fetchByCoursetype("GoogleDrive");
		return new ResponseEntity<List<Course>>(driveList, HttpStatus.OK);
	}
	
	@GetMapping("/courselistbyname/{coursename}")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Course>> getCourseListByName(@PathVariable String coursename) throws Exception
	{
		Course courseList = courseService.fetchCourseByCoursename(coursename);
		System.out.println(courseList.getCoursename()+" ");
		List<Course> courselist = new ArrayList<>();
		courselist.add(courseList);
		return new ResponseEntity<List<Course>>(courselist, HttpStatus.OK);
	}
	
	@GetMapping("/professorlistbyemail/{email}")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Professor>> getProfessorListByEmail(@PathVariable String email) throws Exception
	{
		List<Professor> professors = professorService.getProfessorsByEmail(email);
		return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
	}
	
	@PostMapping("/addProfessor")
	@CrossOrigin(origins = LoginController.ApiURL)
	public Professor addNewProfessor(@RequestBody Professor professor) throws Exception
	{
		Professor professorObj = null;
		String newID = getNewID();
		professor.setProfessorid(newID);
		professorObj = professorService.addNewProfessor(professor);
		professorService.updateStatus(professor.getEmail());
		return professorObj;
	}
	
	@PostMapping("/addCourse")
	@CrossOrigin(origins = LoginController.ApiURL)
	public Course addNewCourse(@RequestBody Course course) throws Exception
	{
		Course courseObj = null;
		String newID = getNewID();
		course.setCourseid(newID);
		
		courseObj = courseService.addNewCourse(course);
		return courseObj;
	}
	
	@PostMapping("/addnewchapter")
	@CrossOrigin(origins = LoginController.ApiURL)
	public Chapter addNewChapters(@RequestBody Chapter chapter) throws Exception
	{
		Chapter chapterObj = null;
		chapterObj = chapterService.addNewChapter(chapter);
		return chapterObj;
	}
	
	@GetMapping("/acceptstatus/{email}")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<String>> updateStatus(@PathVariable String email) throws Exception
	{
		professorService.updateStatus(email);
		List<String> al=new ArrayList<>();
		al.add("accepted");
		return new ResponseEntity<List<String>>(al,HttpStatus.OK);
	}
	
	@GetMapping("/rejectstatus/{email}")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<String>> rejectStatus(@PathVariable String email) throws Exception
	{
		professorService.rejectStatus(email);
		List<String> al=new ArrayList<>();
		al.add("rejected");
		return new ResponseEntity<List<String>>(al,HttpStatus.OK);
	}

	@CrossOrigin(origins =LoginController.ApiURL)
	@PostMapping("/resetProfessorpassword/{email}/{password}")
	public ResponseEntity<Professor> resetpassword(@PathVariable String email,@PathVariable String password) throws Exception
	{
		Professor user=professorService.fetchProfessorByEmail(email);
		if(email!=null||password!=null){
			String pass=RegistrationController.encrypt(password);
			user.setPassword(pass);
		}
		professorService.saveProfessor(user);
		return new ResponseEntity<Professor>(user, HttpStatus.OK);
	}
	
	@GetMapping("/professorprofileDetails/{email}")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Professor>> getProfileDetails(@PathVariable String email) throws Exception
	{

		List<Professor> professors = professorService.fetchProfileByEmail(email);
		if (professors == null || professors.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		// Decrypt passwords for each user before returning the response
		for (Professor user : professors) {
			String decryptedPassword =RegistrationController.decrypt(user.getPassword());
			user.setPassword(decryptedPassword);
		}

		return new ResponseEntity<>(professors, HttpStatus.OK);
	}


	
	@PutMapping("/updateprofessor")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<Professor> updateProfessorProfile(@RequestBody Professor professor) throws Exception
	{
		String currPassword = RegistrationController.encrypt(professor.getPassword());
		System.out.println(currPassword);
		professor.setPassword(currPassword);
		Professor professorobj = professorService.updateProfessorProfile(professor);

		return new ResponseEntity<Professor>(professorobj, HttpStatus.OK);
	}
	
	@GetMapping("/gettotalprofessors")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Integer>> getTotalProfessors() throws Exception
	{
		List<Professor> professors = professorService.getAllProfessors();
		List<Integer> professorsCount = new ArrayList<>();
		professorsCount.add(professors.size());
		return new ResponseEntity<List<Integer>>(professorsCount, HttpStatus.OK);
	}
	
	@GetMapping("/gettotalchapters")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Integer>> getTotalChapters() throws Exception
	{
		List<Chapter> chapters = chapterService.getAllChapters();
		List<Integer> chaptersCount = new ArrayList<>();
		chaptersCount.add(chapters.size());
		return new ResponseEntity<List<Integer>>(chaptersCount, HttpStatus.OK);
	}
	
	@GetMapping("/gettotalcourses")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Integer>> getTotalCourses() throws Exception
	{
		List<Course> courses = courseService.getAllCourses();
		List<Integer> coursesCount = new ArrayList<>();
		coursesCount.add(courses.size());
		return new ResponseEntity<List<Integer>>(coursesCount, HttpStatus.OK);
	}
	
	@GetMapping("/gettotalwishlist")
	@CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<Integer>> getTotalWishlist() throws Exception
	{
		List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
		List<Integer> wishlistCount = new ArrayList<>();
		wishlistCount.add(wishlists.size());
		return new ResponseEntity<List<Integer>>(wishlistCount, HttpStatus.OK);
	}
  
  @GetMapping("/getcoursenames")
  @CrossOrigin(origins = LoginController.ApiURL)
	public ResponseEntity<List<String>> getCourseNames() throws Exception
	{
		List<Course> courses = courseService.getAllCourses();
		List<String> coursenames = new ArrayList<>();
		for(Course obj : courses)
		{
			coursenames.add(obj.getCoursename());
		}
		return new ResponseEntity<List<String>>(coursenames, HttpStatus.OK);
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
