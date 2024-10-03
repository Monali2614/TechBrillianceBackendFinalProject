package com.application.services;

import java.util.List;

import com.application.model.Chapter;
import com.application.model.Review;
import com.application.model.TransactiobDetails;

import com.application.repository.ReviewRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.application.model.User;
import com.application.repository.UserRepository;


@Service
public class UserService 
{
	private static final String KEY="rzp_test_tem0H7TFgmpa9q";
	private static final String KEY_SECRET="mJNWLwctr2HwRkC3480x1YXq";
	private static final String CURRENCY="INR";
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ReviewRepository reviewrepo;

	public Review addReview(Review review){
		return  reviewrepo.save(review);
	}

	public List<Review> getAllReview()
	{
		return (List<Review>)reviewrepo.findAll();
	}
	
	public User saveUser(User user)
	{
		return userRepo.save(user);
	}
	
	public User updateUserProfile(User user)
	{
		return userRepo.save(user);
	}
	
	public List<User> getAllUsers()
	{
		return (List<User>)userRepo.findAll();
	}
	
	public User fetchUserByEmail(String email)
	{
		return userRepo.findByEmail(email);
	}
	
	public User fetchUserByUsername(String username)
	{
		return userRepo.findByUsername(username);
	}
	
	public User fetchUserByEmailAndPassword(String email, String password)
	{
		return userRepo.findByEmailAndPassword(email, password);
	}
	
	public List<User> fetchProfileByEmail(String email)
	{
		return (List<User>)userRepo.findProfileByEmail(email);
	}

	public TransactiobDetails createtransaction(Double amount){
		try{
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("amount",amount*100);
			jsonObject.put("currency",CURRENCY);
			RazorpayClient razorpayClient= new RazorpayClient(KEY,KEY_SECRET);
			Order order=razorpayClient.orders.create(jsonObject);
			System.out.println(order);
			return prepareTransactiondetails(order);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	private TransactiobDetails prepareTransactiondetails(Order order){
		String orderId=order.get(("id"));
		String currency = order.get("currency");
		Integer amount = order.get("amount");
		TransactiobDetails transactiobDetails= new TransactiobDetails(orderId,currency,amount,KEY);
		return transactiobDetails;

	}


}
