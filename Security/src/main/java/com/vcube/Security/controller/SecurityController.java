package com.vcube.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vcube.Security.Entity.User;
import com.vcube.Security.Repository.UserRepository;

@Controller
public class SecurityController {
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/login")
	public String Loginpage()
	{
		return "login";
	}
	@GetMapping("/")
	public String rootDirect()
	{
		return "redirect:/login";
	}
	@GetMapping("/register")
	public String registerPage()
	{
		return "register";
	}
	@PostMapping("/register")
	public String register(User user)
	{
		user.setPassword(encoder.encode(user.getPassword()));
	//	user.setRole("USER");
		repo.save(user);
		return "redirect:/login";
	}
	@GetMapping("/admin/home")
	public String adminHome()
	{
		return "admin-home";
	}
	@GetMapping("/user/home")
	public String userhome()
	{
		return "user-home";
	}
	public String registerUser(@ModelAttribute User user)
	{
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("USER");
			repo.save(user);
			return "redirect:/login";
	}
	

}
