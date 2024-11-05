package com.springboot.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.DTO.JwtRequest;
import com.springboot.blogging.DTO.JwtResponse;
import com.springboot.blogging.JWT.JwtHelper;


@RestController
@RequestMapping("api/jwt")
public class JwtController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	
	@PostMapping("/login")
	public JwtResponse createToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		JwtResponse response=new JwtResponse();
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		try {
		this.authenticationManager.authenticate(authenticationToken);
		UserDetails userByUsername = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtHelper.generateToken(userByUsername);
		response.setToken(token);
		}
		catch(BadCredentialsException e)
		{
            e.printStackTrace();
			response.setToken("Invalid username and password. Please try Again.");
		}
		return response;
	}
}
