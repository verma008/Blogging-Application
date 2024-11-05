package com.springboot.blogging.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springboot.blogging.JWT.JwtAuthenticationFilter;

@Configuration
public class JwtSecurityConfig {

	@Autowired
	 private JwtAuthenticationFilter filter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
		.csrf(csrf->csrf.disable())
		.cors(cors->cors.disable())
		.authorizeHttpRequests()
		.requestMatchers("/api/users/create").permitAll()
		.requestMatchers("/api/jwt/login").permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
		.anyRequest().authenticated();
//		
//		.anyRequest().permitAll();
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
