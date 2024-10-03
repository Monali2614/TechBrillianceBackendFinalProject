package com.application.model;

import ch.qos.logback.classic.db.names.TableName;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
public class User
{
	@Id
	private String email;

	private String username;
	private String userid;
	private String mobile;
	private String gender;
	private String profession;
	private String address;
	private String password;

//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private ImageEntity profileImage;

//	public ImageEntity getProfileImage() {
//		return profileImage;
//	}
//
//	public void setProfileImage(ImageEntity profileImage) {
//		this.profileImage = profileImage;
//	}

	public User()
	{
		super();
	}

	public User(String email, String username, String userid, String mobile, String gender, String profession, String address, String password)
	{
		super();
		this.email = email;
		this.username = username;
		this.userid = userid;
		this.mobile = mobile;
		this.gender = gender;
		this.profession = profession;
		this.address = address;
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getProfession()
	{
		return profession;
	}

	public void setProfession(String profession)
	{
		this.profession = profession;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}