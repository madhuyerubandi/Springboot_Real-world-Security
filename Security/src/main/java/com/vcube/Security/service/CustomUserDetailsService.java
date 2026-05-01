package com.vcube.Security.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vcube.Security.Entity.User;
import com.vcube.Security.Repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userrepo;
	  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
		{
			 User user=userrepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
			 return org.springframework.security.core.userdetails.User
					   .builder()
					  .username(user.getUsername())
					  .password(user.getPassword())
					  .roles(user.getRole())
					  .build();
		}
	

}

















/*
 1.UserDetialsService ->
 
 In Spring Security, the UserDetailsService is a core interface used for loading user-specific data during authentication.
When a user tries to log in, Spring Security calls this service to:
Fetch user data (username, password, roles)
Verify credentials.

2.UserDetails  is a core interface that provides necessary user informantion-such as,usernane,ps to the authentication provider
(usally chekcs username annd passowrd).
3.loadUserByUsername() = method that fetches user from DB during login
 */