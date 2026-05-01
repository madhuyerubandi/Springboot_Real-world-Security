package com.vcube.Security.config;



import java.util.Set;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf(csrf ->csrf.disable())
		.authorizeHttpRequests(auth ->auth
		.requestMatchers("/register","/login").permitAll()
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/user/**").hasRole("USER")
		.anyRequest().authenticated())
		.formLogin(form->form.loginPage("/login")
		.successHandler(successHandler())
		.permitAll()
				)
		.logout(logout ->logout.logoutSuccessUrl("/login"));
		return http.build();
	}
			
		@Bean
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
		@Bean		
		public AuthenticationSuccessHandler successHandler()
		{
			return(request,response,authentication)->
			{
			   Set<String> role=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
				{
					if(role.contains("ROLE_ADMIN"))
					{
						response.sendRedirect("/admin/home");
						return;
						
					}
					else if(role.contains("ROLE_USER"))
					{
						response.sendRedirect("/user/home");
						return;
						
					}
					else
					{
						response.sendRedirect("/login?error");
						return;
					}
				}
			};
	}

}



/*
1. securtiyFilterChain->
👉 👉 We use SecurityFilterChain to define how Spring Security should handle incoming HTTP requests (login, authorization, protection rules).
Anyone can access your endpoints
No login protection
No role control (USER/ADMIN)
No security rules

👉 Your app is completely open
👉 It runs before your controller[p
👉 It is the core of Spring Security configuration

2. 👉 filterChain(HttpSecurity http) = Because Spring uses HttpSecurity as the main configuration object to define web security rules.
login
logout
CSRF
authorization rules
session management
request protection
3.CSRF is a security attack where:(CROSS SITE REQUEST FORGERY)
👉 A malicious website tricks a user’s browser into sending a request to another site where the user is already logged in.
4.authorizeHttpRequest()-> is used to define who can access which URLs (endpoints) in your application.
------------------------------------------------------------------------
5.You use AuthenticationSuccessHandler  to fully control what happens immediately after a successful login instead of default redirection.
🔹 What custom actions you can do
You’re not limited to redirect. You can:
✔ Redirect based on role
✔ Return JSON (for REST APIs)
✔ Save login time in DB
✔ Log audit details
*/
